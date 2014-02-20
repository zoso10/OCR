package ocr.training;

import java.io.IOException;
import ocr.testing.MnistTesting;

public class Training {

    public static void main(String[] args) throws IOException{
        
        int[] numTrainImagesAr = {1000,5000,10000};
        int[] numCellsAr = {2,5};
        
        for(int numCells : numCellsAr) {
            for(int numTrainImages : numTrainImagesAr) {
                String[] params = {intToStr(numTrainImages), intToStr(numCells)};
                MnistTraining.main(params);
                MnistTesting.main(params);
            }
        }
            
    }
    
    private static String intToStr(int i) {
        return new Integer(i).toString();
    }
}
