import main.model.Iris;
import main.util.Experiment;
import main.util.Logintojas;
import main.util.Skaitymas;

import java.util.List;


void main() {
    Skaitymas skaitymas = new Skaitymas();

    Logintojas.inicializuoti();

    List<Iris> visiDuomenys = skaitymas.skaityti();


    for(int K = 1; K <= 20; K++)
    {
        System.out.println("------------------");
        System.out.println("Eksperimento numeris: " + K);
        Experiment exp = new Experiment(visiDuomenys);
        Logintojas.issaugoti(K, exp.getParametrai(), exp.getResult());
    }


}
