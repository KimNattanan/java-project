package utils;

import background.Background;
import entity.Boss;
import entity.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import ui.GameTimer;
import ui.LoveBar;

public class GamePanel extends Canvas {
    public GraphicsContext gc = this.getGraphicsContext2D();
    public Player plr = new Player(this);
    public Boss boss = new Boss(this);
    public Background background = new Background();
    public long time;
    public final long bgTime = (long)(10e9);
    public GameTimer timer;
    public LoveBar loveBar;

    public GamePanel(double w,double h){
        super(w,h);

        background.insert("bg2","bg2.png");
        background.setBg2("bg2");
        timer = new GameTimer(this.getWidth()/2,0,background);
        loveBar = new LoveBar(this,boss,plr);

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
        boss.upd(dt);
        loveBar.upd(dt);
    }
    public void paintComponent(){
        background.draw(gc);
        boss.draw(gc);
        plr.draw(gc);
        timer.draw(gc);
        loveBar.draw(gc);
    }
}
