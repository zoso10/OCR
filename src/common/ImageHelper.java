package common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageHelper {

    public static int[][] toPixelMatrix(BufferedImage G) {
        int m = G.getWidth();
        int n = G.getHeight();
        int[][] pixelMatrix = new int[m][n];
        
        for(int i = 0; i < m; ++i) {
            for(int j = 0; j < n; ++j) {
//                 Getting negatives with JavaFX Image to BufferedImage??
                pixelMatrix[i][j] = Math.abs(G.getRGB(i, j));
            }
        }
        
        return pixelMatrix;
    }
    
    public static void printImage(BufferedImage G, String filename) {
        try {
            ImageIO.write(G, "png", new File(filename));
        } catch(IOException ex) { System.out.println(ex); }
    }
    
    public static int countPixels(int[][] matrix) {
        int pixels = countPixels(matrix, 0, 0, matrix.length, matrix[0].length);
        
        return pixels;
    }
    
    public static int countPixels(int[][] matrix, int x, int y, int width, int height) {
        int pixels = 0;
        
        for(int i = x; i < x+width; ++i) {
            for(int j = y; j < y+height; ++j) {
                if(matrix[i][j] > Config.THRESHOLD) { ++pixels; }
            }
        }
        
        return pixels;
    }
    
    // TODO: Decide if this is needed
//    public int[][] scaleMatrix(int[][] M) {
//        
//        return M
//    }
}
