package utils;

import javafx.scene.input.KeyCode;
import java.util.ArrayList;

public class KeyHandler {
    private static final ArrayList<KeyCode> keyPressed = new ArrayList<>();

    public static boolean getKeyPressed(KeyCode keycode) {
        return keyPressed.contains(keycode);
    }
    public static void setKeyPressed(KeyCode keycode, boolean pressed) {
        if (pressed && !keyPressed.contains(keycode)){
            keyPressed.add(keycode);
        }
        else if(!pressed){
            keyPressed.remove(keycode);
        }
    }
}
