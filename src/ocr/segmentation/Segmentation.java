package ocr.segmentation;

import common.Config;
import common.ImageHelper;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Segmentation {
    
    public List<BufferedImage> segment(BufferedImage G) {
        return Segmentation.segmentWords(G);
    }
    
    public static List<BufferedImage> segmentWords(BufferedImage G){
        
        List<BufferedImage> letters = new LinkedList<>();
        List<Integer> currentWord = null;
        
        for(int x = 0; x < G.getWidth(); ++x) {
            
            if(hasBlackPixel(G, x)) {
                if(currentWord == null) {
                    currentWord = new ArrayList<>();
                }
                currentWord.add(x);
            }
            else if(currentWord != null) {
                int width = currentWord.get(currentWord.size()-1) - currentWord.get(0);
                int height = G.getHeight();
                BufferedImage subimage = G.getSubimage(currentWord.get(0), 0, width, height);
                letters.add(subimage);
                ImageHelper.printImage(subimage, "segmented_character.png");
                currentWord = null;
            }            
        }
        
        // There should not be any trailing whitespace
        int width = currentWord.get(currentWord.size()-1) - currentWord.get(0);
        int height = G.getHeight();
        BufferedImage subimage = G.getSubimage(currentWord.get(0), 0, width, height);
        letters.add(subimage);
        
        // TODO: Take out when functioning properly
//        letters.add(G);
        
        return letters;
    }
    
    private static boolean hasBlackPixel(BufferedImage G, int col) {
        
        for(int y = 0; y < G.getHeight(); ++y) {
            int color = Math.abs(G.getRGB(col, y));
            if(color > Config.THRESHOLD) { return true; }
        }
        return false;
    }
}
