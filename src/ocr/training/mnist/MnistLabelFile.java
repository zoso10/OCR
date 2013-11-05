package ocr.training.mnist;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MnistLabelFile extends MnistDbFile{

    public MnistLabelFile(String name, String mode) throws FileNotFoundException, IOException {
        super(name, mode);
    }
    
    public int readLabel() throws IOException {
        return readUnsignedByte();
    }
    
    @Override
    protected int getMagicNumber() {
        return 2049;
    }
}
