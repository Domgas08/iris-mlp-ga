package main.evolution;

import main.model.Iris;
import main.model.Parametrai;
import main.neural.Tinklas;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class EvuliucinisAlgoritmas {

    private final Random rand = new Random();
    Populiacija population;
    Parametrai parametrai;

    public EvuliucinisAlgoritmas(Parametrai parametrai) {
        this.parametrai = parametrai;
        population = new Populiacija(parametrai.POPULIATION_SIZE, parametrai.CHROMOSOME_SIZE);
    };


    public void generatePopulation()
    {
        population.fillWithRandom(rand);
    }

    public Chromosoma getabsoluteWinnerChromosome()
    {
        return this.population.getBestChromosoma();
    }



    //Ištrinti vėliau
    public void printPopulation()
    {
        for(Chromosoma ch : this.population.getVisasChromosomos())
        {
            System.out.println(ch);
        }
    }

    private Chromosoma mutated (Chromosoma chromosome)
    {
        double[] naujiGenai = new double[chromosome.getGenai().length];
        System.arraycopy(chromosome.getGenai(), 0, naujiGenai, 0, naujiGenai.length);
        for(int i = 0; i < naujiGenai.length; i++)
        {
            if(rand.nextDouble() < parametrai.MUTATION_CHANCE) {
                double change = (rand.nextDouble() * 2 - 1) * parametrai.MUTATION_SIZE;
                naujiGenai[i] += change;
            }
        }

        return new Chromosoma(naujiGenai);

    }

    private Chromosoma crossover(Chromosoma parent1, Chromosoma parent2)
    {
        int crossPoint = rand.nextInt(parametrai.CHROMOSOME_SIZE);
        double[] genai = new double[parametrai.CHROMOSOME_SIZE];
        System.arraycopy(parent1.getGenai(), 0, genai, 0, crossPoint);
        System.arraycopy(parent2.getGenai(), 0, genai, crossPoint, parametrai.CHROMOSOME_SIZE-crossPoint);
        return new Chromosoma(genai);
    }



    public void makeNextGeneration(List<Iris> trainingData)
    {
        fitPopulation(trainingData);
        Chromosoma[] parents = chooseWinnersElite();
        Populiacija newPopulation = new Populiacija(parametrai.POPULIATION_SIZE, parametrai.CHROMOSOME_SIZE);

        //laimėtojus keliam į kitą generation
        for(int i = 0; i < parents.length; i++)
        {
            newPopulation.setChromosoma(i, parents[i]);
        }

        for(int i = parents.length; i < parametrai.POPULIATION_SIZE; i++)
        {
            Chromosoma parent1 = parents[rand.nextInt(parents.length)];
            Chromosoma parent2 = parents[rand.nextInt(parents.length)];


            newPopulation.setChromosoma(i, mutated(crossover(parent1, parent2)));
        }

        this.population = newPopulation;

        fitPopulation(trainingData);
    }


    private Chromosoma[] chooseWinnersElite()
    {
        int numberOfWinners = (int)(parametrai.POPULIATION_SIZE*parametrai.WINNERS_PROCENTAS);
        Chromosoma[] winners = new Chromosoma[numberOfWinners];
        population.sort();

        for(int i = 0; i < numberOfWinners; i++)
        {
            winners[i] = population.getChromosoma(i);
        }

        return winners;
    }


    private void fitPopulation(List<Iris> trainingData)
    {

        IntStream.range(0, parametrai.POPULIATION_SIZE)
                .parallel()
                .forEach(i -> {

                    fitness(population.getChromosoma(i), trainingData);

                });

    }


    //surandam koks chromosomos fitness su MSE
    private void fitness(Chromosoma chromosoma, List<Iris> trainingData)
    {
        double score = 0;
        Tinklas tinklas = new Tinklas(parametrai);
        tinklas.setValuesFromChromoseGenes(chromosoma.getGenai());
        for (Iris iris : trainingData)
        {
            double prognoze = tinklas.prognoze(iris);
            score += (prognoze - iris.getSpecies())*(prognoze - iris.getSpecies());
        }

        score = score/trainingData.size();

        chromosoma.setFitness(score);
    }



}
