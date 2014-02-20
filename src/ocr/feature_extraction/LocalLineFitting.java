package ocr.feature_extraction;

import common.Config;
import common.ImageHelper;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class LocalLineFitting implements FEMethod {

    private final int cells;
    private int totalPixels;
    private int[][] pixelMatrix;
    private double[] featureVector;
    
    public LocalLineFitting(int cells) {
        this.cells = cells;
    }
    
    @Override
    public void setPixelMatrix(int[][] pixelMatrix) {
        this.pixelMatrix = pixelMatrix;
        featureVector = new double[3*cells];
        totalPixels = ImageHelper.countPixels(pixelMatrix);
    }

    @Override
    public void compute() {
        int cellSize = pixelMatrix.length / (int)Math.sqrt(cells);
        
        for(int i = 0; i < pixelMatrix.length-cellSize; i += cellSize) {
            for(int j = 0; j < pixelMatrix[0].length - cellSize; j += cellSize) {
                double b = slope(i, j, cellSize);
                if(Double.isNaN(b)) { b = 10000; }
                int startIndex = 3 * (i / cellSize) + 12 * (j / cellSize);
                featureVector[startIndex] = density(i, j, cellSize);
                featureVector[startIndex + 1] = f2(b);
                featureVector[startIndex + 2] = f3(b);
            }
        }
    }

    @Override
    public double[] getFeatureVector() {
        return featureVector;
    }
    
    private double slope(int x, int y, int cellSize) {
        // Least Squares Fitting
        SimpleRegression sr = new SimpleRegression();
        
        for(int i = x; i < x+cellSize; ++i) {
            for(int j = y; j < y+cellSize; ++j) {
                if(pixelMatrix[i][j] > Config.THRESHOLD) { sr.addData(i, j); }
            }
        }
        
        return sr.getSlope();
    }
    
    private double density(int x, int y, int cellSize) {
        int totalCellPixels = ImageHelper.countPixels(pixelMatrix, x, y, cellSize, cellSize);
        
        return totalCellPixels / (totalPixels * 1.0);
    }
    
    private double f2(double b) {
        
        return (2 * b) / (1 + b * b);
    }
    
    private double f3(double b) {
        
        return (1 - b * b) / (1 + b * b);
    }
}
