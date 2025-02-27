package entity;

import javafx.scene.input.KeyCode;
import utils.AnimLoader;
import utils.AudioController;
import utils.GamePanel;
import utils.KeyHandler;
import javafx.scene.media.MediaPlayer;

public class Player extends Entity {
    private final GamePanel gamePanel;
    private final AnimLoader idleAnim = new AnimLoader("player/idle",1);
    private final AnimLoader workAnim = new AnimLoader("player/work",0.2);
    private final AnimLoader sleepAnim = new AnimLoader("player/sleep",3.2);
    private final AnimLoader dieAnim = new AnimLoader("player/die",1);

    public Player(GamePanel gamePanel){
        super(600,600,300,600);
        this.gamePanel = gamePanel;
        setAction("idle");
        translate(gamePanel.getWidth()/2,gamePanel.getHeight()+100);

    }

    public void upd(long dt){
        if(!getAction().equals("sleep") && !getAction().equals("die")) {
            if (KeyHandler.getKeyPressed(KeyCode.W) && !gamePanel.getIsRewardable()) setAction("work");
            else setAction("idle");
        }

        super.upd(dt);
    }
    public void setAction(String action){
        if(getAction().equals(action)) return;
        switch (action) {
            case "work" -> {
                stopAllSound();
                AudioController.play("plrWork",MediaPlayer.INDEFINITE,0);
                setAction(action, workAnim);
            }
            case "sleep" -> {
                stopAllSound();
                setAction(action, sleepAnim);
            }
            case "die" -> {
                stopAllSound();
                setAction(action, dieAnim);
                gamePanel.setIsGameOver(true);
            }
            default -> {
                stopAllSound();
                setAction(action, idleAnim);
            }
        }
    }
    public void stopAllSound(){
        AudioController.stop("plrWork",0);
    }

}
