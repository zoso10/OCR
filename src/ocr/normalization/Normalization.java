package ocr.normalization;

import common.Config;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Normalization {

    public BufferedImage normalize(BufferedImage G) {
        
        // Right now all we need is scaling
        return scale(G);
    }
    
    private static BufferedImage scale(BufferedImage G) {
        AffineTransform transformer = new AffineTransform();
        BufferedImage scaledImg = new BufferedImage(Config.IMAGE_WIDTH, Config.IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = scaledImg.createGraphics();
        int longerDimLength = G.getHeight() > G.getWidth() ? G.getHeight() : G.getWidth();
        int shorterDimLength = G.getHeight() > G.getWidth() ? G.getWidth() : G.getHeight();
        double scaleFactor = 20.0/longerDimLength;
        int scaledShorterDim = (int)(scaleFactor * shorterDimLength);
        
        // Clear Image
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, Config.IMAGE_WIDTH, Config.IMAGE_HEIGHT);
        
        // This depends on the longer dimension!!
        transformer.translate((Config.IMAGE_WIDTH-scaledShorterDim)/2, 4);
        transformer.scale(scaleFactor, scaleFactor);
        
        g2d.drawImage(G, transformer, null);
        
        return scaledImg;
    }
}
