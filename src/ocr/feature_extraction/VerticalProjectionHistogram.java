package ocr.feature_extraction;

import common.Config;

public class VerticalProjectionHistogram implements FEMethod {

    private double[] featureVector;
    private int[][] pixelMatrix;
    
    @Override
    public void setPixelMatrix(int[][] pixelMatrix) {
        this.pixelMatrix = pixelMatrix;
        featureVector = new double[pixelMatrix[0].length];
    }

    @Override
    public void compute() {
        // Vertical count of the number of foreground pixels
        for(int i = 0; i < pixelMatrix[0].length; ++i) {
            int count = 0;
            
            for(int j = 0; j < pixelMatrix.length; ++j) {
                if(pixelMatrix[j][i] > Config.THRESHOLD) { ++count; }
            }
            
            featureVector[i] = count;
        }
    }

    @Override
    public double[] getFeatureVector() {
        return featureVector;
    }

    
}
