package main.util;

import main.model.Iris;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Skaitymas {


    public List<Iris> skaityti() {
        List<Iris> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/data/Iris.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(Konstantos.COMMA_DELIMITER);
                records.add(new Iris(values[0], values[1], values[2], values[3], values[4], values[5]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return records;

    }
}
