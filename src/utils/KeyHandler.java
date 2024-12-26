package utils;

import javafx.scene.input.KeyCode;
import java.util.ArrayList;

public class KeyHandler {
    private static final ArrayList<KeyCode> keyPressed = new ArrayList<>();
    private static double mouseX,mouseY;

    public static boolean getKeyPressed(KeyCode keycode) {
        return keyPressed.contains(keycode);
    }
    public static void setKeyPressed(KeyCode keycode, boolean pressed) {
        if (pressed && !keyPressed.contains(keycode)) keyPressed.add(keycode);
        else if(!pressed) keyPressed.remove(keycode);
    }

    public static void setMousePos(double x,double y){
        mouseX = x;
        mouseY = y;
    }
    public static double getMouseX(){
        return mouseX;
    }
    public static double getMouseY(){
        return mouseY;
    }
}
