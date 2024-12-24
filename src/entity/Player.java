package entity;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import utils.AnimLoader;
import utils.KeyHandler;

public class Player extends Entity {
    private final AnimLoader idleAnim = new AnimLoader();
    private final AnimLoader workAnim = new AnimLoader();
    private final AnimLoader eatAnim = new AnimLoader();

    public Player(Canvas canvas){
        super(600,600,300,600);
        loadAnims();
        setAction("idle");
        translate(canvas.getWidth()/2,canvas.getHeight()+100);
    }

    public void loadAnims(){
        idleAnim.loadFromDir("player/idle");
        workAnim.loadFromDir("player/work");
        eatAnim.loadFromDir("player/idle");
        idleAnim.setDuration(1);
        workAnim.setDuration(0.2);
        eatAnim.setDuration(1);
    }
    public void upd(long dt){
        if(KeyHandler.getKeyPressed(KeyCode.ENTER)) setAction("work");
        else if (KeyHandler.getKeyPressed(KeyCode.E)) setAction("eat");
        else setAction("idle");

        super.upd(dt);
    }
    public void setAction(String action){
        super.setAction(action);
        if(action.equals("work")) setCurAnim(workAnim);
        else if (action.equals("eat")) setCurAnim(eatAnim);
        else setCurAnim(idleAnim);
    }

}
