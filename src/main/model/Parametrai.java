package main.model;

import main.util.Konstantos;

public class Parametrai {

    //tinklo parametrai
    public int MIDDLE_NEURON_NUM;
    public int CHROMOSOME_SIZE;


    //algoritmo parametrai
    public int POPULIATION_SIZE;
    public double WINNERS_PROCENTAS;
    public double MUTATION_SIZE;
    public double MUTATION_CHANCE;

    //duomenų parametrai
    public int NUMBER_OF_GENERATIONS;

    public Parametrai(int MIDDLE_NEURON_NUM, int POPULIATION_SIZE, double WINNETS_PROCENTAS, double MUTATION_SIZE, double MUTATION_CHANCE, int GENERATION_NUMBER) {
        this.MIDDLE_NEURON_NUM = MIDDLE_NEURON_NUM;
        this.CHROMOSOME_SIZE =  MIDDLE_NEURON_NUM * (Konstantos.IN_NEURON_NUM + Konstantos.OUT_NEURON_NUM + 1) + Konstantos.OUT_NEURON_NUM;
        this.POPULIATION_SIZE = POPULIATION_SIZE;
        this.WINNERS_PROCENTAS = WINNETS_PROCENTAS;
        this.MUTATION_SIZE = MUTATION_SIZE;
        this.MUTATION_CHANCE = MUTATION_CHANCE;
        this.NUMBER_OF_GENERATIONS = GENERATION_NUMBER;

    }


    @Override
    public String toString() {
        return "=== Eksperimento main.model.Parametrai ===\n" +
                String.format("• Vidurinis sluoksnis (Neuronai): %d\n", MIDDLE_NEURON_NUM) +
                String.format("• Chromosomos dydis (Svoriai):  %d\n", CHROMOSOME_SIZE) +
                String.format("• Populiacijos dydis:           %d\n", POPULIATION_SIZE) +
                String.format("• Elito dalis (Winners):        %.2f%%\n", (WINNERS_PROCENTAS * 100), WINNERS_PROCENTAS) +
                String.format("• Mutacijos žingsnis (Size):    %.4f\n", MUTATION_SIZE) +
                String.format("• Mutacijos tikimybė (Chance):  %.2f%%\n", (MUTATION_CHANCE * 100)) +
                String.format("• Generacijų skaičius:          %d\n", NUMBER_OF_GENERATIONS) +
                "================================";
    }


}