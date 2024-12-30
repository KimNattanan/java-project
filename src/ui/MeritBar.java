package ui;

import entity.Boss;
import entity.Player;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import utils.Fonts;
import utils.GamePanel;
import utils.KeyHandler;

public class MeritBar extends Bar {
    private final Canvas canvas;
    private final Boss boss;
    private final Player plr;
    private final double increaseSpeed = 0.3; // portion per sec
    private final double decreaseSpeed = -0.35; // portion per sec
    private final Color barColor = Color.rgb(255,56,176);
    private final Color barColorHover = Color.rgb(255,133,205);

    public MeritBar(Canvas canvas, Boss boss, Player plr){
        super(0,33,canvas.getWidth()/2,25,2, "MERIT",Fonts.getDefault(1,FontWeight.BOLD));
        setBarColor(barColor);
        setBorderColor(Color.BLACK);
        setTitleColor(Color.WHITE);
        this.canvas = canvas;
        this.boss = boss;
        this.plr = plr;
    }
    public void upd(long dt){
        if(!GamePanel.getIsRewardable()) {
            BoundingBox bossBox = boss.getBoundingBox();
            if ((bossBox.getMinX() > 0 && bossBox.getMinX() < canvas.getWidth()) ||
                    (bossBox.getMaxX() > 0 && bossBox.getMaxX() < canvas.getWidth())) {
                if (plr.getAction().equals("work")) { // increase
                    setSpeed(increaseSpeed);
                } else { // decrease
                    setSpeed(decreaseSpeed);
                }
            } else { // no change
                setSpeed(0);
            }

            if (getVal() == 1) {
                GamePanel.setIsRewardable(true);
            }
        }
        else{
            setSpeed(0);
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
