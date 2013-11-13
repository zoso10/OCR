package ocr.neural_network;

import java.io.File;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

public class NeuralNetworkBuilder<B extends NeuralNetworkBuilder<B>> {
    
    private NeuralNetwork instance;
    
    public NeuralNetworkBuilder() {
        this(new NeuralNetwork());
    }
    
    protected NeuralNetworkBuilder(NeuralNetwork network) {
        instance = network;
    }
    
    public static NeuralNetworkBuilder<?> create() {
        return new NeuralNetworkBuilder(new NeuralNetwork());
    }
    
    public B inputNeurons(int n) {
        instance.setInputNeurons(n);
        
        return (B)this;
    }
    
    public B hiddenNeurons(int n) {
        instance.setHiddenNeurons(n);
        
        return (B)this;
    }
    
    public B outputNeurons(int n) {
        instance.setOutputNeurons(n);
        
        return (B)this;
    }
    
    // I think this is a separate action
//    public B training(double[][] actual, double[][] ideal) {
//        
//        if(instance.getNetwork() == null) {
//            instance.createNetwork();
//        }
//        
//        instance.trainNetwork(actual, ideal);
//                
//        return null;
//    }
    
    public B fromFile(String filename) {
        instance.setNetwork((BasicNetwork) EncogDirectoryPersistence.loadObject(new File(filename)));
        
        return (B)this;
    }
    
    public NeuralNetwork build() {
        
        if(instance.getNetwork() == null){
            instance.createNetwork();
        }
        
        return instance;
    }
}
