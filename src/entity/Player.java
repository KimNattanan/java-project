package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import utils.AnimLoader;
import utils.KeyHandler;

public class Player {
    private final AnimLoader idleAnim = new AnimLoader();
    private final AnimLoader workAnim = new AnimLoader();
    private final AnimLoader eatAnim = new AnimLoader();
    private AnimLoader curAnim = idleAnim;

    public Player(){
        loadAnims();
    }

    public void loadAnims(){
        idleAnim.loadFromDir("player/idle");
        workAnim.loadFromDir("player/work");
        eatAnim.loadFromDir("player/idle");
        idleAnim.setDuration(1);
        workAnim.setDuration(0.2);
        eatAnim.setDuration(1);
    }
    public Image getFrame(){
        return curAnim.getFrame();
    }
    public void upd(long dt){
        if(KeyHandler.getKeyPressed(KeyCode.ENTER)){
            setAction("work");
        } else if (KeyHandler.getKeyPressed(KeyCode.E)) {
            setAction("eat");
        }
        else{
            setAction("idle");
        }

        curAnim.upd(dt);
    }
    public void draw(GraphicsContext gc){
        gc.drawImage(curAnim.getFrame(),0,0,600,600);
    }
    public void setAction(String action){
        if(action.equals("work")) curAnim = workAnim;
        else if (action.equals("eat")) curAnim = eatAnim;
        else curAnim = idleAnim;
    }

}
