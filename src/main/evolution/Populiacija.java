package main.evolution;

import main.util.Konstantos;

import java.util.Arrays;
import java.util.Random;

public class Populiacija {
    private Chromosoma[] chromosomos;



    public Populiacija(int populationSize, int chromosomeSize) {
        this.chromosomos = new Chromosoma[populationSize];
        // Iškart inicializuojame tuščius Chromosomų objektus, kad nebūtų null
        for (int i = 0; i < populationSize; i++) {
            this.chromosomos[i] = new Chromosoma(chromosomeSize);
        }
    }

    public void fillWithRandom(Random rand)
    {
        for(Chromosoma chromosoma : this.chromosomos)
        {
            double[] genai = chromosoma.getGenai();
            for(int i = 0; i < genai.length; i++)
            {
                genai[i] = Konstantos.RAND_LOW_BOUND + (Konstantos.RAND_HIGH_BOUND - Konstantos.RAND_LOW_BOUND)*rand.nextDouble();
            }
        }

    }

    public void sort()
    {
        Arrays.sort(this.chromosomos); // Kadangi main.evolution.Chromosoma turi compareTo, tai veiks idealiai
    }

    public Chromosoma getBestChromosoma()
    {
        this.sort();
        return chromosomos[0];
    }

    public Chromosoma[] getVisasChromosomos() {
        return chromosomos;
    }
    public Chromosoma getChromosoma(int index){
        return this.chromosomos[index];
    }
    public void setChromosoma(int index, Chromosoma chromosoma) {
        this.chromosomos[index] = chromosoma;
    }
}
