package ocr.segmentation;

import common.Config;
import common.ImageHelper;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class Segmentation {
    
    public List<BufferedImage> segment(BufferedImage G) {
        List<BufferedImage> separatedChars = new LinkedList<>();
        List<BufferedImage> lines = lineSegmentation(G);
        
        for(BufferedImage line : lines) {
            List<BufferedImage> characters = characterSegmentation(line);
//            ImageHelper.printImage(line, "line.png");
            
            for(BufferedImage character : characters) {
//                ImageHelper.printImage(character, "character.png");
                BufferedImage trimmed = trim(character);
//                ImageHelper.printImage(trimmed, "trimmed.png");
                separatedChars.add(trimmed);
            }
        }
        
        return separatedChars;
    }
    
    private static List<BufferedImage> lineSegmentation(BufferedImage G) {        
        int width = G.getWidth()-1;
        int y0 = -1;
        int y1 = -1;
        List<BufferedImage> lines = new LinkedList<>();
        
        for(int y = 0; y < G.getHeight(); ++y) {            
            if(rowHasBlackPixel(G, y)) {
                if(y0 == -1) { y0 = y; }
                y1 = y;
            } else if (y0 != -1 && y0 != y1) {
                BufferedImage subimage = G.getSubimage(0, y0, width, y1 - y0);
                lines.add(subimage);
                y0 = y1 = -1;
            }      
        }
        // In case the image ends on a black pixel we want to get the last line
        if(y0 != -1 && y0 != y1) {
            BufferedImage subimage = G.getSubimage(0, y0, width, y1 - y0);
            lines.add(subimage);            
        }
        
        return lines;
    }
    
    private static List<BufferedImage> characterSegmentation(BufferedImage G) {
        int height = G.getHeight()-1;
        int x0 = -1;
        int x1 = -1;
        List<BufferedImage> characters = new LinkedList<>();
        
        for(int x = 0; x < G.getWidth(); ++x) {
            if(colHasBlackPixel(G, x)) {
                if(x0 == -1) { x0 = x; }
                x1 = x;
            } else if (x0 != -1 && x0 != x1) {
//                System.out.println(String.format("x1:%d x0:%d", x1, x0));
                BufferedImage subimage = G.getSubimage(x0, 0, x1-x0, height);
                characters.add(subimage);
                x0 = x1 = -1;
            }
        }
        // In case the image ends on a black pixel we want to get the last character
        if(x0 != -1 && x0 != x1) {
            BufferedImage subimage = G.getSubimage(x0, 0, x1-x0, height);
            characters.add(subimage);
        }
        
        return characters;
    }
    
    private static BufferedImage trim(BufferedImage G) {
        int width = G.getWidth()-1;
        int y0 = -1;
        int y1 = -1;
        
        for(int y = 0; y < G.getHeight(); ++y) {            
            if(rowHasBlackPixel(G, y)) {
                if(y0 == -1) { y0 = y; }
                y1 = y;
            } else if (y0 != -1 && y0 != y1) {
                return G.getSubimage(0, y0, width, y1 - y0);
            }      
        }

        return G.getSubimage(0, y0, width, y1 - y0);
    }
    
    private static boolean rowHasBlackPixel(BufferedImage G, int row) {        
        for(int x = 0; x < G.getWidth(); ++x) {
            int color = Math.abs(G.getRGB(x, row));
            if(color > Config.THRESHOLD) { return true; }
        }
        return false;
    }
    
    private static boolean colHasBlackPixel(BufferedImage G, int col) {
        for(int y = 0; y < G.getHeight(); ++y) {
            int color = Math.abs(G.getRGB(col, y));
            if(color > Config.THRESHOLD) { return true; }
        }
        return false;
    }    
}
