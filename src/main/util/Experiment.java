package main.util;

import main.evolution.Chromosoma;
import main.evolution.EvuliucinisAlgoritmas;
import main.model.Iris;
import main.model.Parametrai;
import main.neural.Tinklas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Experiment {

    private record ReziaiDouble(double min, double max) {}
    private record ReziaiInt(int min, int max) {}

    private static final ReziaiInt middleNeuronNumRiba = new ReziaiInt(2, 10);
    private static final ReziaiInt populationSizeRiba = new  ReziaiInt(10, 10000);
    private static final ReziaiDouble winnersProcentRiba = new ReziaiDouble(0.15, 0.2);
    private static final ReziaiDouble mutationSizeRiba = new  ReziaiDouble(0.05, 0.15);
    private static final ReziaiDouble mutationChanceRiba = new ReziaiDouble(0.05, 0.15);
    private static final ReziaiInt numberOfGenerationsRiba = new ReziaiInt(10, 1000);

    private static final Random rand = new Random();
    private double tinkamumoProcentas;
    private Parametrai parametrai;

    public Experiment(List<Iris> visiDuom) {
        this(visiDuom, setRandomParametrai());
    }


    public Experiment(List<Iris> visiDuom, Parametrai parametrai)
    {
        this.parametrai = parametrai;
        //kad nesugadint orginalo
        List<Iris> visiDuomenys = new ArrayList<>(visiDuom);

        Collections.shuffle(visiDuomenys, new Random());
        //Suskirstoma į dvi testavimo ir treniravimosi dalis
        List<Iris> testingData = visiDuomenys.subList(0, (int)(Konstantos.TESTAVIMO_PROCENTAS*visiDuomenys.size()));
        List<Iris> trainingData = visiDuomenys.subList((int)(Konstantos.TESTAVIMO_PROCENTAS*visiDuomenys.size()), visiDuomenys.size());

        EvuliucinisAlgoritmas ea = new EvuliucinisAlgoritmas(parametrai);
        ea.generatePopulation();

        for(int K = 1; K <= parametrai.NUMBER_OF_GENERATIONS; K++)
        {
            ea.makeNextGeneration(trainingData);

            //progressbar'as kas 30 generacijų [pagražintas su AI pagalba, kad nebūtų nuobodu žiūrėt]
            if (K % 30 == 0 || K == parametrai.NUMBER_OF_GENERATIONS) {
                int procentai = (K * 100) / parametrai.NUMBER_OF_GENERATIONS;

                // Gėlytės stadija pagal progresą
                String gele;
                if      (procentai < 20)  gele = "🌱";
                else if (procentai < 40)  gele = "🌿";
                else if (procentai < 60)  gele = "🌼";
                else if (procentai < 80)  gele = "🌸";
                else                      gele = "🌺";

                // Žiedlapiai: ✿ užaugę, · dar ne
                StringBuilder bar = new StringBuilder();
                int uzauge = procentai / 4;
                for (int k = 0; k < 25; k++) {
                    bar.append(k < uzauge ? "✿" : "·");
                }

                double klaida = ea.getabsoluteWinnerChromosome().getFitness();
                System.out.print("\r" + gele + " " + bar + " " + procentai + "% | Klaida: " + String.format("%.4f", klaida) + "  ");
            }


        }



        //---------Testavimas---------
        Chromosoma bestChromosoma = ea.getabsoluteWinnerChromosome();
        Tinklas testavimoTinklas = new Tinklas(parametrai);
        testavimoTinklas.setValuesFromChromoseGenes(bestChromosoma.getGenai());
        int teisingiSpejimai = 0;
        for (Iris iris : testingData)
        {
            double prognoze = testavimoTinklas.prognoze(iris);

            // Suapvaliname prognozę iki artimiausio sveikojo skaičiaus (1, 2 arba 3)
            int suapvalintaPrognoze;


            //<=1.5, tai pirma klase (Iris-setosa), jei atsakymas  nuo 1.5 iki 2.5 tai antra klasė (Iris-versicolor),
            //jei atsakymas nuo 2.5, tai atsakymas trečia klasė (Iris-virginica).
            if(prognoze <= Konstantos.FIRST_CLASS_LIMIT) {
                suapvalintaPrognoze = 1;
            }
            else if(prognoze <= Konstantos.SECOND_CLASS_LIMIT) {
                suapvalintaPrognoze = 2;
            }
            else {
                suapvalintaPrognoze = 3;
            }



            // Tikriname, ar suapvalintas spėjimas idealiai sutampa su tikra gėlės rūšimi
            if (suapvalintaPrognoze == iris.getSpecies()) {
                teisingiSpejimai++;
            }
        }

        tinkamumoProcentas = (double)(teisingiSpejimai)/testingData.size();

        System.out.println();
        System.out.println("Teisingų spėjimų skaičius: " + teisingiSpejimai);
        System.out.println("Gerai atspėtų gelių procentas: " + (double)(teisingiSpejimai)/testingData.size());

    }

    public double getResult()
    {
        return this.tinkamumoProcentas;
    }

    public Parametrai getParametrai() {
        return parametrai;
    }

    static int getRandomNumber(ReziaiInt ribos) {
        if (ribos.min() > ribos.max()) {
            throw new IllegalArgumentException("Min reikšmė negali būti didesnė už Max!");
        }
        return rand.nextInt(ribos.min(), ribos.max() + 1);
    }

    static double getRandomNumber(ReziaiDouble ribos) {
        if (ribos.min() > ribos.max()) {
            throw new IllegalArgumentException("Min reikšmė negali būti didesnė už Max!");
        }
        return (ribos.min() + (ribos.max() - ribos.min()) * rand.nextDouble());
    }


    static Parametrai setRandomParametrai()
    {
        int RandMidNeuronNum = getRandomNumber(middleNeuronNumRiba);
        int RandPopulationSize = getRandomNumber(populationSizeRiba);
        double RandWinnersProcent = getRandomNumber(winnersProcentRiba);
        double RandMutationSize = getRandomNumber(mutationSizeRiba);
        double RandMutationChance = getRandomNumber(mutationChanceRiba);
        int RandNumberOfGenerations = getRandomNumber(numberOfGenerationsRiba);

        return new Parametrai(RandMidNeuronNum, RandPopulationSize, RandWinnersProcent, RandMutationSize, RandMutationChance, RandNumberOfGenerations);

    }


}

