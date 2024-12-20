package utils;

import entity.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class GamePanel extends Canvas {
    public GraphicsContext gc = this.getGraphicsContext2D();
    public Player plr = new Player();
    public GamePanel(double w,double h){
        super(w,h);
        this.setVisible(true);
        addKeyListener();
    }
    public void addKeyListener() {
        this.setOnKeyPressed((KeyEvent event) -> KeyHandler.setKeyPressed(event.getCode(), true));
        this.setOnKeyReleased((KeyEvent event) -> KeyHandler.setKeyPressed(event.getCode(), false));
    }
    public void upd(long dt) throws InterruptedException {
        plr.upd(dt);
    }
    public void paintComponent(){
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,this.getWidth(),this.getHeight());
        plr.draw(gc);
    }
}
