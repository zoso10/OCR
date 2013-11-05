package ocr;

import ocr.feature_extraction.FeatureExtraction;
import ocr.neural_network.NeuralNetwork;
import ocr.normalization.Normalization;
import ocr.segmentation.Segmentation;


public class OCRBuilder<B extends OCRBuilder<B>> {
    
    private OCR instance;
    
    public OCRBuilder() {
        this(new OCR());
    }
    
    protected OCRBuilder(OCR ocr) {
        instance = ocr;
    }
    
    public static OCRBuilder<?> create() {
        
        return new OCRBuilder();
    }
    
    public B segmentation(Segmentation segmentation) {        
        instance.setSegmentation(segmentation);
        
        return (B)this;
    }
    
    public B normalization(Normalization normalization) {
        instance.setNormalization(normalization);
        
        return (B)this;
    }
    
    public B featureExtraction(FeatureExtraction featureExtraction) {
        instance.setFeatureExtraction(featureExtraction);
        
        return (B)this;
    }
    
    public B neuralNetwork(NeuralNetwork neuralNetwork) {
        instance.setNeuralNetwork(neuralNetwork);
        
        return (B)this;
    }
    
    public OCR build() {
        return instance;
    }
}
