package ocr.feature_extraction;

import common.Config;

public class HorizontalCelledProjection implements FEMethod {
    
    private final int cells;
    private int[][] pixelMatrix;
    private double[] featureVector;
    
    public HorizontalCelledProjection(int cells) {
        this.cells = cells;
    }
    
    @Override
    public void setPixelMatrix(int[][] pixelMatrix) {
        // TODO: Write cleaner code
        this.pixelMatrix = pixelMatrix;
        int addition = pixelMatrix.length % cells == 0 ? 0 : pixelMatrix.length;
        int length = (cells * pixelMatrix.length) + addition;
        featureVector = new double[length];
    }
    
    @Override
    public void compute() {
        int m = pixelMatrix.length;
        int n = pixelMatrix[0].length;
        double q = n / cells;
        
        for(int i = 0; i < m; ++i) {
            for(int j = 0; j < n; ++j) {
                if(pixelMatrix[i][j] > Config.THRESHOLD) {
                    int index = (int)(i + m * Math.floor((j-1) / q));
                    featureVector[index] = 1.0;
                }
            }
        }
    }
    
    @Override
    public double[] getFeatureVector() {
        
        return featureVector;
    }
}
