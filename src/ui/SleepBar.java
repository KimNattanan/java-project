package ui;

import entity.Boss;
import entity.Player;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import utils.Fonts;
import utils.KeyHandler;

public class SleepBar extends Bar {
    private final Player plr;
    private final double increaseSpeed = 0.2; // portion per sec
    private final double decreaseSpeed = -0.03; // portion per sec
    private final Color barColor = Color.rgb(95,0,255);
    private final Color barColorHover = Color.rgb(163,111,255);

    public SleepBar(Canvas canvas, Player plr){
        super(0,17,canvas.getWidth()/2,15,2, "AWAKENESS",Fonts.getDefault(0,FontWeight.BOLD));
        setBarColor(barColor);
        setBorderColor(Color.BLACK);
        setTitleColor(Color.WHITE);
        this.plr = plr;
        setVal(1);
    }
    public void upd(long dt){
        if(!plr.getAction().equals("die")) {
            if (plr.getAction().equals("sleep")) {
                setSpeed(increaseSpeed);
                if (getVal() == 1) plr.setAction("idle");
            } else {
                setSpeed(decreaseSpeed);
                if (getVal() == 0) plr.setAction("sleep");
            }
        }

        if(KeyHandler.getMouseX() >= getX() && KeyHandler.getMouseX() <= getX()+getW() &&
           KeyHandler.getMouseY() >= getY() && KeyHandler.getMouseY() <= getY()+getH()
        ){
            setBarColor(barColorHover);
        }
        else{
            setBarColor(barColor);
        }

        super.upd(dt);
    }
}
