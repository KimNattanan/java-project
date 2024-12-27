package ui;

import entity.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import utils.Fonts;
import utils.KeyHandler;

public class EnergyBar extends Bar {
    private final Player plr;
    private final double normalSpeed = -0.05; // portion per sec
    private final double workSpeed = -0.075; // portion per sec
    private final double sleepSpeed = -0.01; // portion per sec
    private final double bentoValue = 0.7;
    private final Color barColor = Color.rgb(0,149,87);
    private final Color barColorHover = Color.rgb(123,255,201);

    public EnergyBar(Canvas canvas, Player plr){
        super(0,1,canvas.getWidth()/2,15,2, "ENERGY",Fonts.getDefault(0,FontWeight.BOLD));
        setBarColor(barColor);
        setBorderColor(Color.BLACK);
        setTitleColor(Color.WHITE);
        this.plr = plr;
        setVal(1);
    }
    public void upd(long dt){
        if(getVal()==0){
            plr.setAction("die");
        }
        if(plr.getAction().equals("work")){
            setSpeed(workSpeed);
        }
        else if(plr.getAction().equals("sleep")){
            setSpeed(sleepSpeed);
        }
        else{
            setSpeed(normalSpeed);
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
    public void eatBento(){
        setVal(getVal()+bentoValue);
    }
}
