package common;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Config {
    
    // Image Settings
    public static final int THRESHOLD = 100;
    public static final int IMAGE_HEIGHT = 28;
    public static final int IMAGE_WIDTH = 28;

    // Neural Network Settings
    public static final int OUTPUT_NEURONS = 10;
    public static final double ERROR_THRESHOLD = 0.015;
    public static final double EPOCH_THRESHOLD = 750;
    public static final String NN_FILENAME = "neural_network.eg";
    public static final char[] CHARACTER_LOOKUP = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    
    // MNIST Database
    public static final String MNIST_TRAIN_IMAGES = "src/mnist/resources/train-images-idx3-ubyte";
    public static final String MNIST_TRAIN_LABELS = "src/mnist/resources/train-labels-idx1-ubyte";
    public static final String MNIST_TEST_IMAGES = "src/mnist/resources/t10k-images-idx3-ubyte";
    public static final String MNIST_TEST_LABELS = "src/mnist/resources/t10k-labels-idx1-ubyte";
    
    // Logging
    public static Logger getLogger(String name) {
        
        Logger l = Logger.getLogger(name);
        try{
            Handler h = new FileHandler("data.log");
            Formatter f = new SimpleFormatter();
            h.setFormatter(f);
            l.addHandler(h);
        } catch(IOException | SecurityException e) { System.out.println(e); }
        
        return l;
    }
    
}
