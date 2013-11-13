package ocr.feature_extraction;

import common.Config;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FeatureExtraction {

    private int featureVectorLength;
    private final List<FEMethod> methods;
    
    public FeatureExtraction() {
        methods = new LinkedList<>();
    }
    
    public void setPixelMatrix(int[][] pixelMatrix) {
        featureVectorLength = 0;
        
        for(FEMethod m : methods) {
            m.setPixelMatrix(pixelMatrix);
            featureVectorLength += m.getFeatureVector().length;
        }
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
        double[] primitiveFeatureVector = new double[featureVectorLength];
        int index = 0;
        
        for(FEMethod m : methods) {
            for(double d : m.getFeatureVector()) {
                primitiveFeatureVector[index++] = d;
            }
        }
        
        return primitiveFeatureVector;
    }
}
