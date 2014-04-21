package ocr.testing;

import common.Config;
import common.PrintHelper;
import java.io.IOException;
import java.util.logging.Level;
import ocr.OCR;
import ocr.OCRBuilder;
import ocr.feature_extraction.FeatureExtractionBuilder;
import ocr.feature_extraction.HorizontalCelledProjection;
import ocr.feature_extraction.HorizontalProjectionHistogram;
import ocr.feature_extraction.LocalLineFitting;
import ocr.feature_extraction.VerticalCelledProjection;
import ocr.feature_extraction.VerticalProjectionHistogram;
import ocr.neural_network.NeuralNetworkBuilder;
import ocr.training.mnist.MnistManager;

public class MnistTesting {
    
    /*
     * ARGS Organization:
     * 0 -> Number of Train Images
     * 1 -> Number of cells for HorizontalCelledProjection
     * 2 -> Number of cells for VerticalCelledProjection
     */
    public static void main(String[] args) throws IOException{
        
        // Use all 10,000 test images
        int numCorrect = 0;
        int[][] image;
        MnistManager m = new MnistManager(Config.MNIST_TEST_IMAGES, Config.MNIST_TEST_LABELS);
        OCR ocr = OCRBuilder
                        .create()
                        .featureExtraction(
                            FeatureExtractionBuilder
                                .create()
                                .children(
                                          new HorizontalCelledProjection(5),
                                          new VerticalCelledProjection(5),
                                          new HorizontalProjectionHistogram(),
                                          new VerticalProjectionHistogram(),
                                          new LocalLineFitting(49))
                                .build()
                            )
                        .neuralNetwork(
                            NeuralNetworkBuilder
                                .create()
                                .fromFile(Config.NN_FILENAME)
                                .build()
                            )
                        .build();
        
        // Do some rudimentary benchmarking
        long start = System.currentTimeMillis();
        for(int i = 1; i <= 10000; ++i) {
            m.setCurrent(i);
            image = m.readPixelMatrix();
            
            
            
            String digitStr = ocr.identify(image);
            int digit = new Integer(digitStr).intValue();
            int actual = m.readLabel();
            
            if(digit == actual) { ++numCorrect; }
            else{
//                System.out.println(String.format("Identified: %d, Actual: %d, Index: %d, Output vector: ", digit, actual, i));
//                PrintHelper.prettyPrintArray(outputVector);
            }
        }
        long end = System.currentTimeMillis();
        
        System.out.println(String.format("Num correct: %d, Accuracy: %f\n", numCorrect, (numCorrect / 10000.0) * 100.0));
        System.out.println("Total Recognition time is " + (end - start) + "ms.");
//        Config.getLogger(MnistTesting.class.getName()).log(Level.INFO, "Accuracy: {0}", (numCorrect / 10000.0) * 100.0);
        
    }
}
