package ocr.feature_extraction;

import common.Config;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FeatureExtraction {

    private List<Double> featureVector;
    private List<FEMethod> methods;
    
    public FeatureExtraction() {
        methods = new LinkedList<>();
    }
    
    public void setPixelMatrix(int[][] pixelMatrix) {
        for(FEMethod m : methods) {
            m.setPixelMatrix(pixelMatrix);
        }
        
        featureVector = new ArrayList<>(Config.INPUT_NEURONS);
    }
    
    public void addMethods(FEMethod... m) {
        methods.addAll(Arrays.asList(m));
    }
    
    public void compute() {
        for(FEMethod m : methods) {
            m.compute();
        }
    }
    
    public double[] getFeatureVector() {
        for(FEMethod m : methods) {
            for(double d : m.getFeatureVector()) {
                featureVector.add(d);
            }
        }
        
        if(Config.INPUT_NEURONS != featureVector.size()) {
            throw new RuntimeException("Neural Network input and Feature Vector are different sizes.");
        }
        
        double[] primitiveFeatureVector = new double[Config.INPUT_NEURONS];
        
        for(int i = 0; i < featureVector.size(); ++i) {
            primitiveFeatureVector[i] = featureVector.get(i);
        }
        
        return primitiveFeatureVector;
    }
}
