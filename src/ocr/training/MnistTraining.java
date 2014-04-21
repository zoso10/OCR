package ocr.training;

import common.Config;
import java.io.IOException;
import ocr.feature_extraction.FeatureExtraction;
import ocr.feature_extraction.FeatureExtractionBuilder;
import ocr.feature_extraction.HorizontalCelledProjection;
import ocr.feature_extraction.HorizontalProjectionHistogram;
import ocr.feature_extraction.LocalLineFitting;
import ocr.feature_extraction.VerticalCelledProjection;
import ocr.feature_extraction.VerticalProjectionHistogram;
import ocr.neural_network.NeuralNetwork;
import ocr.neural_network.NeuralNetworkBuilder;
import ocr.training.mnist.MnistManager;

public class MnistTraining {
    
    /*
     * ARGS Organization:
     * 0 -> Number of Train Images
     * 1 -> Number of cells for HorizontalCelledProjection
     * 2 -> Number of cells for VerticalCelledProjection
     */
    public static void main(String[] args) throws IOException{
        
        int numTrainImages = new Integer(new Integer(args[0]));
        double[][] actual = new double[numTrainImages][];
        double[][] ideal = new double[numTrainImages][];
        MnistManager m = new MnistManager(Config.MNIST_TRAIN_IMAGES, Config.MNIST_TRAIN_LABELS);
        
        FeatureExtraction fe = FeatureExtractionBuilder
                                .create()
                                .children(
//                                          new HorizontalCelledProjection(new Integer(args[1])), 
//                                          new VerticalCelledProjection(new Integer(args[1])),
                                          new HorizontalCelledProjection(5), 
                                          new VerticalCelledProjection(5),
                                          new HorizontalProjectionHistogram(),
                                          new VerticalProjectionHistogram(),
                                          new LocalLineFitting(49))
                                .build();
        
        // Build Training Data
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
        
        int inputNeurons = fe.getFeatureVector().length;
        int hiddenNeurons = (2/3) * inputNeurons;
        
        NeuralNetwork nn = NeuralNetworkBuilder
                                .create()
                                .inputNeurons(inputNeurons)
                                .hiddenNeurons(hiddenNeurons)
                                .outputNeurons(Config.OUTPUT_NEURONS)
                                .build();
        
        System.out.println("Beginning training");
        nn.trainNetwork(actual, ideal);
        
        System.out.println("Saving Network");
        nn.persistNetwork();
        
    }
    
}
