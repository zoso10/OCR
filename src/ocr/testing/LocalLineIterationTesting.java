package ocr.testing;

import common.Config;
import common.ImageHelper;
import java.awt.image.BufferedImage;
import java.io.IOException;
import ocr.training.mnist.MnistManager;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class LocalLineIterationTesting {
    
    // Below lies the random "testing"(?), try not to cringe
    public static void main(String[] args) throws IOException{
        
        int cells = 16;
        MnistManager mm = new MnistManager(Config.MNIST_TRAIN_IMAGES, Config.MNIST_TRAIN_LABELS);
        
        // Grab first image
        mm.setCurrent(1);
        int[][] image = mm.readPixelMatrix();
        BufferedImage bimg = mm.readImage();
        
        ImageHelper.printImage(mm.readImage(), "llf_test.png");
        System.out.println("The number is: " + mm.readLabel());
        
        // Make sure the cell size is correct -> 7 pixels
        int cellSize = image.length / (int)Math.sqrt(cells);
        System.out.println("The size of the cells are " + cellSize + " pixels");
        
        // Check that the cell iteration is correct
        for(int i = 0; i < image.length; i += cellSize) {
            for(int j = 0; j < image[0].length; j += cellSize) {
                String info = String.format("Top left X: %d Y: %d Bottom right X: %d Y: %d", i, j, i + cellSize, j + cellSize);
                System.out.println(info);
                double b = slope(i, j, cellSize, image);
                System.out.println(b);
                System.out.println("\n\n");
            }
        }
    }
    
    private static double slope(int x, int y, int cellSize, int[][] pixelMatrix) {
        // Least Squares Fitting
        SimpleRegression sr = new SimpleRegression();
        int count = 0;
        
        for(int i = x; i < x+cellSize; ++i) {
            for(int j = y; j < y+cellSize; ++j) {
                if(pixelMatrix[i][j] > Config.THRESHOLD) { sr.addData(i, j); System.out.println(i + " " + j); ++count; }
            }
        }
        
        return sr.getSlope();
    }
    
}
