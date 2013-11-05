package ocr.neural_network;

import common.Config;
import java.io.File;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.EncogDirectoryPersistence;

// Move this helper into NeuralNetwork subclasses
public class NeuralNetworkHelper {

    public static BasicNetwork create(int input, int output) {
        return create(input, output, 0);
    }
    
    public static BasicNetwork create(int input, int output, int hidden) {
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, true, input));
        
        if(hidden != 0){
            network.addLayer(new BasicLayer(new ActivationSigmoid(), true, hidden));
        }
        
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false, output));
        network.getStructure().finalizeStructure();
        network.reset();
        Encog.getInstance().shutdown();
        
        return network;
    }
    
    public static void train(BasicNetwork network, double[][] actual, double[][] ideal) {
        // TODO: Add functionality        
        MLDataSet trainingData = new BasicMLDataSet(actual, ideal);
        MLTrain train = new ResilientPropagation(network, trainingData);
        int epoch = 1;
        
        // We have to do at least 1 iteration to get an error
        do {
            train.iteration();
            System.out.println(String.format("Epoch: %d, Error: %f", epoch++, train.getError()));
        } while(train.getError() > Config.ERROR_THRESHOLD);
    }
    
    public static void persist(String filename, BasicNetwork network) {
        EncogDirectoryPersistence.saveObject(new File(filename), network);
        Encog.getInstance().shutdown();
    }
    
    
}
