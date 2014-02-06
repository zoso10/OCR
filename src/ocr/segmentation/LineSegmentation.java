package ocr.segmentation;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class LineSegmentation {

    private BufferedImage G;
    
    public LineSegmentation(BufferedImage G) {
        this.G = G;
    }
    
    public List<BufferedImage> segment() {
        
        List<BufferedImage> lines = new LinkedList<>();
        int width = G.getWidth();
        int y0 = -1;
        int y1 = -1;
        
        for(int y = 0; y < G.getHeight(); ++y) {
            if(SegmentationHelper.rowHasBlackPixel(G, y)) {
                if(y0 == -1) { y0 = y; }
                y1 = y;             
            } else if(y0 != -1 && y0 != y1) {
                Image subimage =  G.getSubimage(0, y0, width, y1-y0);
                
            }
        }
        
        return null;
    }
    
}
