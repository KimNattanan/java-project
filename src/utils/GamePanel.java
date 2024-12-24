package utils;

import background.Background;
import entity.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class GamePanel extends Canvas {
    public GraphicsContext gc = this.getGraphicsContext2D();
    public Player plr = new Player(this);
    public Background background = new Background();
    public long time;
    public final long bgTime = (long)(10e9);
    public GameTimer timer;

    public GamePanel(double w,double h){
        super(w,h);

        background.insert("bg2","bg2.png");
        background.setBg2("bg2");
        timer = new GameTimer(this.getWidth()/2,0,background);

        this.setVisible(true);
        addKeyListener();
    }
    public void addKeyListener() {
        this.setOnKeyPressed((KeyEvent event) -> KeyHandler.setKeyPressed(event.getCode(), true));
        this.setOnKeyReleased((KeyEvent event) -> KeyHandler.setKeyPressed(event.getCode(), false));
    }
    public void upd(long dt) throws InterruptedException {
        timer.upd(dt);
        plr.upd(dt);
    }
    public void paintComponent(){
        background.draw(gc);
        timer.draw(gc);
        plr.draw(gc);
    }
}
