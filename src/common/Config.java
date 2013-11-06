package common;

public class Config {
    
    // Image Settings
    public static final int THRESHOLD = 100;
    public static final int IMAGE_HEIGHT = 28;
    public static final int IMAGE_WIDTH = 28;

    // Neural Network Settings
    public static final int INPUT_NEURONS = 48;
    public static final int HIDDEN_NEURONS = 40;
    public static final int OUTPUT_NEURONS = 10;
    public static final double ERROR_THRESHOLD = 0.045;
    public static final String FILENAME = "neural_network.eg";
    public static final char[] CHARACTER_LOOKUP = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    
    // MNIST Database
    public static final String MNIST_TRAIN_IMAGES = "src/ocr/training/mnist/train-images-idx3-ubyte";
    public static final String MNIST_TRAIN_LABELS = "src/ocr/training/mnist/train-labels-idx1-ubyte";
    public static final String MNIST_TEST_IMAGES = "src/ocr/training/mnist/t10k-images-idx3-ubyte";
    public static final String MNIST_TEST_LABELS = "src/ocr/training/mnist/t10k-labels-idx1-ubyte";
    
}
