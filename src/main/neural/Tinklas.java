package main.neural;//TODO: pasidaryt cia parametrus

import main.model.Iris;
import main.model.Parametrai;
import main.util.Konstantos;

public class Tinklas {

    Parametrai parametrai;
    private int inLayerNum;
    private int middleLayerNum;
    private int outLayerNum;
    private double[][] wINtoMID;
    private double[][] wMIDtoOUT;
    private double[] biasINtoMID;
    private double[] biasMIDtoOUT;


    public Tinklas(Parametrai parametrai)
    {
        this.parametrai = parametrai;
        middleLayerNum = parametrai.MIDDLE_NEURON_NUM;
        inLayerNum = Konstantos.IN_NEURON_NUM;
        outLayerNum = Konstantos.OUT_NEURON_NUM;

        wINtoMID = new double[inLayerNum][middleLayerNum];
        wMIDtoOUT = new double[middleLayerNum][outLayerNum];
        biasINtoMID = new double[middleLayerNum];
        biasMIDtoOUT = new double[outLayerNum];

    }

    public void setValuesFromChromoseGenes(double[] chromosoma)
    {
        int pozicija=0;

        for(int i = 0; i < inLayerNum; i++){
            System.arraycopy(chromosoma, pozicija, wINtoMID[i], 0, middleLayerNum);
            pozicija+=middleLayerNum;
        }

        System.arraycopy(chromosoma, pozicija, biasINtoMID, 0, middleLayerNum);
        pozicija+=middleLayerNum;

        for(int i = 0; i < middleLayerNum; i++){
            System.arraycopy(chromosoma, pozicija, wMIDtoOUT[i], 0, outLayerNum);
            pozicija+=outLayerNum;
        }

        System.arraycopy(chromosoma, pozicija, biasMIDtoOUT, 0, outLayerNum);
    }




    private double sigmoid (double x)
    {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    private double[] activateArray(double[] array)
    {
        double[] activatedArr = new double[array.length];

        for(int i = 0; i < activatedArr.length; i++)
            activatedArr[i] = sigmoid(array[i]);

        return activatedArr;
    }

    private double[] irisToDoubleArray(Iris iris)
    {
        return new double[]{iris.getSepalLength(), iris.getSepalWidth(), iris.getPetalLength(), iris.getPetalWidth()};
    }



    public double prognoze(Iris iris)
    {
        double[] ivestis = irisToDoubleArray(iris);
        double[] vidurys = new double[middleLayerNum];
        double[] isvestis = new double[outLayerNum];

        for(int midId = 0; midId < middleLayerNum; midId++)
        {
            for(int inId = 0; inId < inLayerNum; inId++)
            {
                vidurys[midId] += ivestis[inId]*wINtoMID[inId][midId];
            }

            vidurys[midId] += biasINtoMID[midId];
        }

        vidurys = activateArray(vidurys);

        for(int outID = 0; outID < outLayerNum; outID++)
        {
            for(int midID = 0; midID < middleLayerNum; midID++)
            {
                isvestis[outID] += vidurys[midID]*wMIDtoOUT[midID][outID];
            }

            isvestis[outID] += biasMIDtoOUT[outID];
        }


        return isvestis[0];

    }




}
