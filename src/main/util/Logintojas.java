package main.util;

import main.model.Parametrai;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Locale; // Reikalinga taško/kablelio suvaldymui

public class Logintojas {

    private static final String CSV_KELIAS = "src/data/rezultatai.csv";

    private static final String ANTRASTE =
            "ExperimentoNr,ViduriniaiNeuronai,ChromosemosDydis,PopuliacijosDydis," +
                    "ElitoDalis,MutacijosDydis,MutacijosTikimybe,GeneracijuSkaicius,TikslumoProcent\n";

    // Kviečiamas vieną kartą programos pradžioje
    public static void inicializuoti() {
        File failas = new File(CSV_KELIAS);

        // Sukuriame tėvinius aplankus (pvz., src/data), jei jie netyčia neegzistuotų
        if (failas.getParentFile() != null) {
            failas.getParentFile().mkdirs();
        }

        // Kadangi darome naują eksperimentų paiešką, ištriname seną failą,
        if (failas.exists()) {
            failas.delete();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(failas))) {
            bw.write(ANTRASTE);
        } catch (IOException e) {
            throw new RuntimeException("Nepavyko sukurti rezultatų failo: " + e.getMessage());
        }
    }

    // Kviečiamas po kiekvieno eksperimento
    public static void issaugoti(int experimentoNr, Parametrai p, double tikslumas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_KELIAS, true))) {
            // SVARBU: Pridedame Locale.US, kad double skaičiai būtų rašomi su tašku (0.1500, o ne 0,1500)
            // Tai apsaugos CSV struktūrą nuo sugadinimo
            String eilute = String.format(Locale.US, "%d,%d,%d,%d,%.4f,%.4f,%.4f,%d,%.4f\n",
                    experimentoNr,
                    p.MIDDLE_NEURON_NUM,
                    p.CHROMOSOME_SIZE,
                    p.POPULIATION_SIZE,
                    p.WINNERS_PROCENTAS,
                    p.MUTATION_SIZE,
                    p.MUTATION_CHANCE,
                    p.NUMBER_OF_GENERATIONS,
                    tikslumas
            );
            bw.write(eilute);
        } catch (IOException e) {
            throw new RuntimeException("Nepavyko išsaugoti rezultato: " + e.getMessage());
        }
    }
}