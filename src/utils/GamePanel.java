package utils;

import background.Background;
import entity.Boss;
import entity.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import ui.EnergyBar;
import ui.GameTimer;
import ui.LoveBar;
import ui.SleepBar;

public class GamePanel extends Canvas {
    public GraphicsContext gc = this.getGraphicsContext2D();
    public Player plr = new Player(this);
    public Boss boss = new Boss(this);
    public Background background = new Background();
    public long time;
    public final long bgTime = (long)(10e9);
    public GameTimer timer;
    public LoveBar loveBar;
    public EnergyBar energyBar;
    public SleepBar sleepBar;

    public GamePanel(double w,double h){
        super(w,h);

        background.insert("bg2","bg2.png");
        background.setBg2("bg2");
        timer = new GameTimer(this,background);
        loveBar = new LoveBar(this,boss,plr);
        energyBar = new EnergyBar(this,plr);
        sleepBar = new SleepBar(this,plr);

        this.setVisible(true);
        addKeyListener();
    }
    public void addKeyListener() {
        this.addEventHandler(KeyEvent.KEY_PRESSED, e->KeyHandler.setKeyPressed(e.getCode(),true));
        this.addEventHandler(KeyEvent.KEY_RELEASED, e->KeyHandler.setKeyPressed(e.getCode(),false));
    }
    public void upd(long dt) throws InterruptedException {
        timer.upd(dt);
        plr.upd(dt);
        boss.upd(dt);
        loveBar.upd(dt);
        energyBar.upd(dt);
        sleepBar.upd(dt);
    }
    public void paintComponent(){
        background.draw(gc);
        boss.draw(gc);
        plr.draw(gc);
        timer.draw(gc);
        loveBar.draw(gc);
        energyBar.draw(gc);
        sleepBar.draw(gc);
    }
}
