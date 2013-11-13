package ocr.feature_extraction;

public class VerticalCelledProjection implements FEMethod {

    private int cells;
    private int[][] pixelMatrix;
    private double[] featureVector;
    
    public VerticalCelledProjection(int cells) {
        this.cells = cells;
    }
    
    @Override
    public void setPixelMatrix(int[][] pixelMatrix) {
        // TODO: Write this cleaner
        this.pixelMatrix = transpose(pixelMatrix);
        // To account for non-divisible cell count
        int addition = pixelMatrix.length % cells == 0 ? 0 : pixelMatrix.length;
        int length = cells * pixelMatrix.length + addition;
        featureVector = new double[length];
    }

    @Override
    public void compute() {
        // We transposed the matrix so we can use the same algorithm
        HorizontalCelledProjection h = new HorizontalCelledProjection(cells);
        h.setPixelMatrix(pixelMatrix);
        h.compute();
        featureVector = h.getFeatureVector();
    }

    @Override
    public double[] getFeatureVector() {
        
        return featureVector;
    }
    
    private int[][] transpose(int[][] matrix) {
        int x = matrix.length;
        int y = matrix[0].length;
        int[][] transposeMat = new int[x][y];
        
        for(int i = 0; i < matrix.length; ++i) {
            for(int j = 0; j < matrix[0].length; ++j) {
                transposeMat[j][i] = matrix[i][j];
            }
        }
        
        return transposeMat;
    }
    
}
