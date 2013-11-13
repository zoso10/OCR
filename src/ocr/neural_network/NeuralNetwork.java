package ocr.neural_network;

import common.Config;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.Layer;

public class NeuralNetwork {
    
    private int inputNeurons;
    private int hiddenNeurons;
    private int outputNeurons;
    private BasicNetwork network;
    
    public NeuralNetwork() {
        inputNeurons = 0;
        hiddenNeurons = 0;
        outputNeurons = 0;
    }
    
    public void createNetwork() {
        if(network != null) { throw new RuntimeException("Network already created. Overwriting existing network\n"); }
        // This condition isn't correct
        //if(inputNeurons == 0 || outputNeurons == 0 || network == null) { throw new RuntimeException("Network topology is empty\n"); }
        
        network = NeuralNetworkHelper.create(inputNeurons, outputNeurons, hiddenNeurons);        
    }
    
    public void trainNetwork(double[][] actual, double[][] ideal) {
        if(network == null) { throw new RuntimeException("Neural Network is null\n"); }
        
        NeuralNetworkHelper.train(network, actual, ideal);
    }
    
    public void persistNetwork() {
        if(network == null) { throw new RuntimeException("Neural Network is null\n"); }
        
        NeuralNetworkHelper.persist(Config.NN_FILENAME, network);
    }
    
    public char compute(double[] featureVector) {
        double[] outputVector = new double[outputNeurons];
        network.compute(featureVector, outputVector);
        char c = vectorToChar(outputVector);
        
        return c;
    }
    
    public int getInputNeurons() {
        return inputNeurons;
    }
    
    public int getHiddenNeurons() {
        return hiddenNeurons;
    }
    
    public int getOutputNeurons() {
        return outputNeurons;
    }
    
    public BasicNetwork getNetwork() {
        //if(network == null) { throw new RuntimeException("Neural Network is null\n"); }
        
        return network;
    }
    
    public void setInputNeurons(int inputNeurons) {
        this.inputNeurons = inputNeurons;
    }
    
    public void setHiddenNeurons(int hiddenNeurons) {
        this.hiddenNeurons = hiddenNeurons;
    }
    
    public void setOutputNeurons(int outputNeurons) {
        this.outputNeurons = outputNeurons;
    }
    
    public void setNetwork(BasicNetwork network) {
        this.network = network;        
        inputNeurons = network.getInputCount();
        outputNeurons = network.getOutputCount();
        hiddenNeurons = 0 - (inputNeurons + outputNeurons);
        for(Layer l : network.getStructure().getLayers()) {
            hiddenNeurons += l.getNeuronCount();
        }        
    }
    
    private char vectorToChar(double[] vector) {
        int maxIndex = maxIndex(vector);
        char c = Config.CHARACTER_LOOKUP[maxIndex];
        
        return c;
    }
    
    private int maxIndex(double[] vector) {
        int maxIndex = -1;
        double max = -1;
        
        for(int i = 0; i < vector.length; ++i) {
            if(max < vector[i]) {
                maxIndex = i;
                max = vector[i];
            }
        }
        
        return maxIndex;
    }
}
