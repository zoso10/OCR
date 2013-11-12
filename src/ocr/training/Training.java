package ocr.training;

import java.io.IOException;

public class Training {

    public static void main(String[] args) throws IOException{
        
        int[] numTrainImagesAr = {1000,5000,10000};
        int[] numCellsAr = {4,8};        
        
        for(int numCells : numCellsAr) {
            int inputNeurons = 0;
            int hiddenNeurons = (2/3) * inputNeurons;
            for(int numTrainImages : numTrainImagesAr) {
                String[] params = {intToStr(numTrainImages),
                                   intToStr(numCells),
                                   intToStr(numCells),
                                   intToStr(inputNeurons),
                                   intToStr(hiddenNeurons)};
                MnistTraining.main(params);
            }
        }
            
    }
    
    private static String intToStr(int i) {
        return new Integer(i).toString();
    }
}
