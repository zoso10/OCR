package common;

import java.text.DecimalFormat;

public class PrintHelper {
    
    public static void prettyPrintArray(double[] outputVector) {
        System.out.println("[");
        DecimalFormat df = new DecimalFormat("#.###");
        for(double d : outputVector) {
            System.out.println(df.format(d) + ", ");
        }
        System.out.println("]");
    }
}
