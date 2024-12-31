package utils;

import background.Background;
import entity.Boss;
import entity.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import ui.EnergyBar;
import ui.GameTimer;
import ui.MeritBar;
import ui.SleepBar;

public class GamePanel extends Canvas {
    public boolean isPause,isGameOver,isRewardable;
    public GraphicsContext gc = this.getGraphicsContext2D();
    public Player plr = new Player(this);
    public Boss boss = new Boss(this);
    public Background background = new Background();
    public GameTimer timer;
    public MeritBar meritBar;
    public EnergyBar energyBar;
    public SleepBar sleepBar;

    public GamePanel(double w,double h){
        super(w,h);

        background.insert("bg2", "background/spot_pattern.png");
        background.setBg2("bg2");
        timer = new GameTimer(this,background);
        meritBar = new MeritBar(this,boss,plr);
        energyBar = new EnergyBar(this,plr);
        sleepBar = new SleepBar(this,plr);

        this.setVisible(true);

        this.setIsGameOver(false);
        this.setIsPause(false);
        this.setIsRewardable(false);
    }
    public void upd(long dt) throws InterruptedException {
        timer.upd(dt);
        plr.upd(dt);
        boss.upd(dt);
        meritBar.upd(dt);
        energyBar.upd(dt);
        sleepBar.upd(dt);
    }
    public void paintComponent(){
        background.draw(gc);
        boss.draw(gc);
        plr.draw(gc);
        timer.draw(gc);
        meritBar.draw(gc);
        energyBar.draw(gc);
        sleepBar.draw(gc);
    }

    public boolean getIsPause(){ return isPause; }
    public void setIsPause(boolean bool){ isPause = bool; }
    public boolean getIsGameOver(){ return isGameOver; }
    public void setIsGameOver(boolean bool){ isGameOver = bool; }
    public boolean getIsRewardable(){ return isRewardable; }
    public void setIsRewardable(boolean bool){ isRewardable = bool; }
}
