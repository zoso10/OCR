package ocr.feature_extraction;

public interface FEMethod {

    public void setPixelMatrix(int[][] pixelMatrix);
    
    public void compute();
    
    public double[] getFeatureVector();
}
