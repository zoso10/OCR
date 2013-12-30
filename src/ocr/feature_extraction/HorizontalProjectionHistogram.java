package ocr.feature_extraction;

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double[] getFeatureVector() {
        return featureVector;
    }

    
}
