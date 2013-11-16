package ocr.testing;

import common.Config;
import common.PrintHelper;
import java.io.IOException;
import java.util.logging.Level;
import ocr.OCR;
import ocr.OCRBuilder;
import ocr.feature_extraction.FeatureExtractionBuilder;
import ocr.feature_extraction.HorizontalCelledProjection;
import ocr.feature_extraction.VerticalCelledProjection;
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
//        int numTestImages = new Integer(args[0]);
        int numCorrect = 0;
        int[][] image;
        MnistManager m = new MnistManager(Config.MNIST_TEST_IMAGES, Config.MNIST_TEST_LABELS);
        
        for(int i = 1; i <= 10000; ++i) {
            m.setCurrent(i);
            image = m.readPixelMatrix();
            
            OCR ocr = OCRBuilder
                        .create()
                        .featureExtraction(
                            FeatureExtractionBuilder
                                .create()
                                .children(new HorizontalCelledProjection(new Integer(args[1])),
                                          new VerticalCelledProjection(new Integer(args[2])))
                                .build()
                            )
                        .neuralNetwork(
                            NeuralNetworkBuilder
                                .create()
                                .fromFile(Config.NN_FILENAME)
                                .build()
                            )
                        .build();
            
            String digitStr = ocr.identify(image);
            int digit = new Integer(digitStr).intValue();
            int actual = m.readLabel();
            
            if(digit == actual) { ++numCorrect; }
            else{
//                System.out.println(String.format("Identified: %d, Actual: %d, Index: %d, Output vector: ", digit, actual, i));
//                PrintHelper.prettyPrintArray(outputVector);
            }
        }
        
        System.out.println(String.format("Num correct: %d, Accuracy: %f\n", numCorrect, (numCorrect / 10000.0) * 100.0));
//        Config.getLogger(MnistTesting.class.getName()).log(Level.INFO, "Accuracy: {0}", (numCorrect / 10000.0) * 100.0);
        
    }
}
