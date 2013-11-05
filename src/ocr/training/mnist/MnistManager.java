package ocr.training.mnist;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MnistManager {

    private MnistImageFile images;
    private MnistLabelFile labels;
    private int[][] image;
    
    public MnistManager(String imagesFile, String labelsFile) throws IOException{
        if(imagesFile != null){
            images = new MnistImageFile(imagesFile, "r");
        }
        if(labelsFile != null){
            labels = new MnistLabelFile(labelsFile, "r");
        }
    }
    
    // I changed functionality here, it might affect some other stuff
    private void iterate() throws IOException{
        if(images == null){
            throw new IllegalStateException("Images file not initialized");
        }
        
        image = images.readImage();
    }
    
    public int[][] readPixelMatrix() throws IOException{
        if(images == null){
            throw new IllegalStateException("Images file not initialized");
        }
        
        iterate();
        
        return image;
    }
    
    public BufferedImage readImage() throws IOException{
        if(images == null){
            throw new IllegalStateException("Images file not initialized");
        }
        BufferedImage bimg = new BufferedImage(image.length, image[0].length, BufferedImage.TYPE_INT_RGB);
        for(int x = 0; x < image.length; ++x){
            for(int y = 0; y < image[0].length; ++y){
                // The colors are inverted
                Color c = image[x][y] < 100 ? Color.WHITE : Color.BLACK;
                bimg.setRGB(x, y, c.getRGB());
            }
        }        
        
        return bimg;
    }
    
    public int readLabel() throws IOException{
        if(labels == null){
            throw new IllegalStateException("Labels file not initialized");
        }
        return labels.readLabel();
    }
    
    public void setCurrent(int index){
        images.setCurrentIndex(index);
        labels.setCurrentIndex(index);
    }
    
    public MnistImageFile getImages(){
        return images;
    }
    
    public MnistLabelFile getLabels(){
        return labels;
    }
}
