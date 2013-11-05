package ocr.training.mnist;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MnistImageFile extends MnistDbFile {

    private int rows;
    private int cols;

    public MnistImageFile(String name, String mode) throws FileNotFoundException, IOException {
        super(name, mode);

        // read header information
        rows = readInt();
        cols = readInt();
    }

    public int[][] readImage() throws IOException {
        int[][] dat = new int[getRows()][getCols()];
        for (int i = 0; i < getCols(); i++) {
            for (int j = 0; j < getRows(); j++) {
                // The image needs transposed
                dat[j][i] = readUnsignedByte();
            }
        }
        return dat;
    }

    public void nextImage() throws IOException {
        super.next();
    }

    public void prevImage() throws IOException {
        super.prev();
    }

    @Override
    protected int getMagicNumber() {
        return 2051;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    @Override
    public int getEntryLength() {
        return cols * rows;
    }

    @Override
    public int getHeaderSize() {
        return super.getHeaderSize() + 8; // to more integers - rows and columns
    }
}
