package utils;

import background.Background;
import entity.Boss;
import entity.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import ui.EnergyBar;
import ui.GameTimer;
import ui.LoveBar;
import ui.SleepBar;

public class GamePanel extends Canvas {
    public static boolean isPause,isGameOver,isRewardable;
    public GraphicsContext gc = this.getGraphicsContext2D();
    public Player plr = new Player(this);
    public Boss boss = new Boss(this);
    public Background background = new Background();
    public GameTimer timer;
    public LoveBar loveBar;
    public EnergyBar energyBar;
    public SleepBar sleepBar;

    public GamePanel(double w,double h){
        super(w,h);

        background.insert("bg2", "background/spot_pattern.png");
        background.setBg2("bg2");
        timer = new GameTimer(this,background);
        loveBar = new LoveBar(this,boss,plr);
        energyBar = new EnergyBar(this,plr);
        sleepBar = new SleepBar(this,plr);

        this.setVisible(true);

        GamePanel.setIsGameOver(false);
        GamePanel.setIsPause(false);
        GamePanel.setIsRewardable(true);
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

    public static boolean getIsPause(){ return isPause; }
    public static void setIsPause(boolean bool){ isPause = bool; }
    public static boolean getIsGameOver(){ return isGameOver; }
    public static void setIsGameOver(boolean bool){ isGameOver = bool; }
    public static boolean getIsRewardable(){ return isRewardable; }
    public static void setIsRewardable(boolean bool){ isRewardable = bool; }
}
