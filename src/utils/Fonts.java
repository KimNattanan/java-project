package utils;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Fonts {
    private final static int[] sz = {16,20,24,28,32,40,64,96,128};
    private final static String defaultFont = "Arial";

    public static Font getDefault(int sizeId, FontWeight fontWeight){
        return Font.font(defaultFont, fontWeight, sz[sizeId]);
    }
    public static Font getDefault(int sizeId, FontWeight fontWeight,boolean italic){
        if(italic) return Font.font(defaultFont, fontWeight, FontPosture.ITALIC, sz[sizeId]);
        return getDefault(sizeId,fontWeight);
    }
}
