package background;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Background {
    private final Map<String, Image> bg = new HashMap<>();
    private Image curBg;
    private Image curBg2;
    private long time;
    public void insert(String name,String path){
        bg.put(name,new Image(String.valueOf(ClassLoader.getSystemResource(path))));
    }
    public void setBg(String key){
        curBg = bg.get(key);
    }
    public void setBg2(String key){
        curBg2 = bg.get(key);
    }
    public void draw(GraphicsContext gc){
        double alpha = gc.getGlobalAlpha();
        if(curBg != null) gc.drawImage(curBg,0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        gc.setGlobalAlpha(0.5);
        if(curBg2 != null) gc.drawImage(curBg2,0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        gc.setGlobalAlpha(alpha);
    }
}
