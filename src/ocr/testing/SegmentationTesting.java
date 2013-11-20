package ocr.testing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import ocr.segmentation.Segmentation;

public class SegmentationTesting {
    
    public static void main(String[] args) throws IOException {
        
        Segmentation seg = new Segmentation();
        BufferedImage bimg = ImageIO.read(new File("src/resources/tyler.png"));
        List<BufferedImage> images = seg.segment(bimg);
        
        System.out.println(images.size());
    }
}
