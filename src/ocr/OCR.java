package ocr;

import common.ImageHelper;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import ocr.feature_extraction.FeatureExtraction;
import ocr.neural_network.NeuralNetwork;
import ocr.normalization.Normalization;
import ocr.segmentation.Segmentation;

/**
 * 
 * @author Tyler Ewing
 */
public class OCR {
    
    private Segmentation segmentation;
    private Normalization normalization;
    private FeatureExtraction featureExtraction;
    private NeuralNetwork neuralNetwork;
    
    /**
     * Returns a string of the characters in the input Image G. 
     * 
     * @param G a binary Image of a string of characters to be identified
     * @return  a string of the recognized characters in the input image G
     */
    public String identify(BufferedImage G) {
        StringBuilder str = new StringBuilder();
        List<BufferedImage> segmentedLetters = new LinkedList<>();
        
        if(featureExtraction == null) { throw new RuntimeException("Feature Extraction is null"); }
        
        if(segmentation != null) {
            segmentedLetters = segmentation.segment(G);
        } else {
            segmentedLetters.add(G);
        }
        
        for(BufferedImage i : segmentedLetters) {
            if(normalization != null) {
                i = normalization.normalize(G);
            }
            
            int[][] pixelMatrix = ImageHelper.toPixelMatrix(i);
            featureExtraction.setPixelMatrix(pixelMatrix);
            featureExtraction.compute();
            char c = neuralNetwork.compute(featureExtraction.getFeatureVector());
            str.append(c);
        }
        
        return str.toString();
    }
    
    /**
     * This method is used mostly for testing.
     * The pixel matrix parameter must be of only a single character. Only feature 
     * extraction and the neural network will be run. There is no normalization or 
     * segmentation, even if they were set when building the OCR object.
     * 
     * @param pixelMatrix   a pixel matrix of a single character
     * @return              a string of the single character recognized from the input pixel matrix
     */
    public String identify(int[][] pixelMatrix) {
        
        if(featureExtraction == null) { throw new RuntimeException("Feature Extraction is null"); }
        
        featureExtraction.setPixelMatrix(pixelMatrix);
        featureExtraction.compute();
        
        char c = neuralNetwork.compute(featureExtraction.getFeatureVector());
        
        return Character.toString(c);
    }
    
    public void setSegmentation(Segmentation segmentation) {
        this.segmentation = segmentation;
    }
    
    public void setNormalization(Normalization normaliztion) {
        this.normalization = normaliztion;
    }
    
    public void setFeatureExtraction(FeatureExtraction featureExtraction) {
        this.featureExtraction = featureExtraction;
    }
    
    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }
}
