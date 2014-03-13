package ocr.testing;

import common.Config;
import ocr.feature_extraction.FeatureExtraction;
import ocr.feature_extraction.FeatureExtractionBuilder;
import ocr.feature_extraction.HorizontalProjectionHistogram;
import ocr.feature_extraction.VerticalProjectionHistogram;
import ocr.training.mnist.MnistManager;

public class HorizontalProjectionHistogramTest {
    
    public static void main(String[] args) throws Exception{
        FeatureExtraction hph = FeatureExtractionBuilder
                                    .create()
                                    .children(new VerticalProjectionHistogram())
                                    .build();
        
        MnistManager mm = new MnistManager(Config.MNIST_TRAIN_IMAGES, Config.MNIST_TRAIN_LABELS);
        
        int[][] pixelMatrix = mm.readPixelMatrix();
        int label = mm.readLabel();
        
        hph.setPixelMatrix(pixelMatrix);
        hph.compute();
     
        System.out.println("The number is: " + label + "\n\n");
        printVector(hph.getFeatureVector());
    }
    
    private static void printVector(double[] v) {
        for(double d : v) {
            System.out.print((int)d + "  ");
        }
        System.out.println();
    }
}
