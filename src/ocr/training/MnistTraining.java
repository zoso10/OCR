package ocr.training;

import common.Config;
import java.io.IOException;
import ocr.feature_extraction.FeatureExtraction;
import ocr.feature_extraction.FeatureExtractionBuilder;
import ocr.feature_extraction.HorizontalCelledProjection;
import ocr.feature_extraction.VerticalCelledProjection;
import ocr.neural_network.NeuralNetwork;
import ocr.neural_network.NeuralNetworkBuilder;
import ocr.training.mnist.MnistManager;

public class MnistTraining {
    
    /*
     * ARGS Organization:
     * 0 -> Number of Train Images
     * 1 -> Number of cells for HorizontalCelledProjection
     * 2 -> Number of cells for VerticalCelledProjection
     * 3 -> Number input neurons
     * 4 -> Number hidden neurons
     */
    public static void main(String[] args) throws IOException{
        
        int numTrainImages = new Integer(args[0]);
        double[][] actual = new double[numTrainImages][];
        double[][] ideal = new double[numTrainImages][];
        MnistManager m = new MnistManager(Config.MNIST_TRAIN_IMAGES, Config.MNIST_TRAIN_LABELS);
        
        FeatureExtraction fe = FeatureExtractionBuilder
                                .create()
                                .children(new HorizontalCelledProjection(new Integer(args[1])), 
                                          new VerticalCelledProjection(new Integer(args[2])))
                                .build();
        
        for(int i = 1; i <= numTrainImages; ++i) {
            // Get Pixel Matrix
            m.setCurrent(i);
            int[][] image = m.readPixelMatrix();
            
            fe.setPixelMatrix(image);
            fe.compute();
            
            // Add to Training Data
            double[] idealVector = new double[Config.OUTPUT_NEURONS];
            idealVector[m.readLabel()] = 1;
            
            actual[i-1] = fe.getFeatureVector();
            ideal[i-1] = idealVector;
        }
        
        NeuralNetwork nn = NeuralNetworkBuilder
                                .create()
                                .inputNeurons(new Integer(args[3]))
                                .hiddenNeurons(new Integer(args[4]))
                                .outputNeurons(Config.OUTPUT_NEURONS)
//                                .training(actual, ideal) // Training is a separate action
                                .build();
        
        nn.trainNetwork(actual, ideal);
        
        nn.persistNetwork();
        
    }
    
}
