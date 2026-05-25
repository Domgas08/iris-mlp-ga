package main.evolution;

public class Chromosoma implements Comparable<Chromosoma> {
    private double[] genai;
    private double fitness;

    // Konstruktorius tuščiai chromosomai (sukuria masyvą)
    public Chromosoma(int chromosome_size) {
        this.genai = new double[chromosome_size];
        this.fitness = Double.MAX_VALUE;
    }

    // Konstruktorius su duomenimis (nukopijuoja paduotus genus)
    public Chromosoma(double[] genai) {
        this.genai = new double[genai.length];
        System.arraycopy(genai, 0, this.genai, 0, genai.length);
    }
    public double[] getGenai() {
        return genai;
    }
    public double getFitness() {
        return fitness;
    }
    public void setGenai(double[] Genai) {
        this.genai = Genai;
    }
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Chromosoma o) {
        return Double.compare(this.fitness, o.fitness);
    }

    @Override
    public String toString() {
        return "Fitness: " + fitness;
    }
}
