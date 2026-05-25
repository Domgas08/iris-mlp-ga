package main.model;

public class Iris {

    private int id;
    double sepalLength;
    double sepalWidth;
    double petalLength;
    double petalWidth;
    private int species;

    public Iris(String id, String sepalLength, String sepalWidth, String petalLength, String petalWidth, String species) {
        this.id = Integer.parseInt(id);
        this.sepalLength = Double.parseDouble(sepalLength);
        this.sepalWidth = Double.parseDouble(sepalWidth);
        this.petalLength = Double.parseDouble(petalLength);
        this.petalWidth = Double.parseDouble(petalWidth);

        //kokia gele pagal skaiciu
        if(species.equals("main.model.Iris-setosa")){this.species = 1;}
        else if (species.equals("main.model.Iris-versicolor")){this.species = 2;}
        else  if (species.equals("main.model.Iris-virginica")){this.species = 3;}
        else {
            throw new IllegalArgumentException("Nežinoma gėlių rūšis: " + species);
        }
    }

    public double getSepalLength() {
        return sepalLength;
    }
    public double getSepalWidth() {
        return sepalWidth;
    }
    public double getPetalLength() {
        return petalLength;
    }
    public double getPetalWidth() {
        return petalWidth;
    }
    public int getSpecies() {
        return species;
    }


    @Override
    public String toString() {
        return id + " " + sepalLength + " " + sepalWidth + " " + petalLength + " " + petalWidth + " "  + species;
    }


}
