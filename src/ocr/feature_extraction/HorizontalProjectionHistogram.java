package ocr.feature_extraction;

import common.Config;

public class HorizontalProjectionHistogram implements FEMethod {

    private double[] featureVector;
    private int[][] pixelMatrix;
    
    @Override
    public void setPixelMatrix(int[][] pixelMatrix) {
        this.pixelMatrix = pixelMatrix;
        featureVector = new double[pixelMatrix.length];
    }

    @Override
    public void compute() {
        // Horizontal count of foreground pixels
        for(int i = 0; i < pixelMatrix.length; ++i) {
            int count = 0;
            
            for(int j = 0; j < pixelMatrix[0].length; ++j) {
                if(pixelMatrix[i][j] > Config.THRESHOLD) { ++count; }
            }
            
            featureVector[i] = count;
        }
    }

    @Override
    public double[] getFeatureVector() {
        return featureVector;
    }

    
}
