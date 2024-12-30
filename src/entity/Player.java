package entity;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import utils.AnimLoader;
import utils.GamePanel;
import utils.KeyHandler;
import javafx.scene.media.MediaPlayer;

public class Player extends Entity {
    private final AnimLoader idleAnim = new AnimLoader("player/idle",1);
    private final AnimLoader workAnim = new AnimLoader("player/work",0.2);
    private final AnimLoader sleepAnim = new AnimLoader("player/sleep",3.2);
    private final AnimLoader dieAnim = new AnimLoader("player/die",1);
    private final MediaPlayer workSound;

    public Player(Canvas canvas){
        super(600,600,300,600);
        setAction("idle");
        translate(canvas.getWidth()/2,canvas.getHeight()+100);

        workSound = new MediaPlayer(new Media(ClassLoader.getSystemResource("player/sound/work.mp3").toString()));
        workSound.setCycleCount(MediaPlayer.INDEFINITE);
        workSound.setOnReady(()->{
            workSound.play();
            workSound.stop();
        });
    }

    public void upd(long dt){
        if(!getAction().equals("sleep") && !getAction().equals("die")) {
            if (KeyHandler.getKeyPressed(KeyCode.W) && !GamePanel.getIsRewardable()) setAction("work");
            else setAction("idle");
        }

        super.upd(dt);
    }
    public void setAction(String action){
        if(getAction().equals(action)) return;
        switch (action) {
            case "work" -> {
                stopAllSound();
                workSound.play();
                setAction(action, workAnim);
            }
            case "sleep" -> {
                stopAllSound();
                setAction(action, sleepAnim);
            }
            case "die" -> {
                stopAllSound();
                setAction(action, dieAnim);
                GamePanel.setIsGameOver(true);
            }
            default -> {
                stopAllSound();
                setAction(action, idleAnim);
            }
        }
    }
    public void stopAllSound(){
        if(workSound != null) workSound.stop();
    }

}
