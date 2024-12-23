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
    public GamePanel(double w,double h){
        super(w,h);
        this.setVisible(true);
        addKeyListener();
        background.insert("0","office/morning.jpg");
        background.insert("1","office/evening.jpg");
        background.insert("2","office/night_light_on.jpg");
        background.insert("3","office/night_light_off.jpg");
        background.setBg("0");
    }
    public void addKeyListener() {
        this.setOnKeyPressed((KeyEvent event) -> KeyHandler.setKeyPressed(event.getCode(), true));
        this.setOnKeyReleased((KeyEvent event) -> KeyHandler.setKeyPressed(event.getCode(), false));
    }
    public void upd(long dt) throws InterruptedException {
        long bg0 = time/bgTime;
        time = (time+dt)%(4*bgTime);
        long bg1 = time/bgTime;
        if(bg0 != bg1) background.setBg(String.valueOf(bg1));
        plr.upd(dt);
    }
    public void paintComponent(){
        background.draw(gc);
        plr.draw(gc);
    }
}
