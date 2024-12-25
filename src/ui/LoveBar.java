package ui;

import entity.Boss;
import entity.Player;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.Fonts;

public class LoveBar {
    private final Canvas canvas;
    private final Boss boss;
    private final Player plr;
    private double love;
    private final double increaseSpeed = 0.2; // portion per sec
    private final double decreaseSpeed = 0.3; // portion per sec
    private final double x=0,y=10;
    private final double w,h,padding=2;
    private final Color borderColor = Color.BLACK;
    private final Color barColor = Color.rgb(255,56,176);

    public LoveBar(Canvas canvas, Boss boss, Player plr){
        this.canvas = canvas;
        this.boss = boss;
        this.plr = plr;
        w = 300;
        h = 20;
    }
    public void upd(long dt){
        BoundingBox bossBox = boss.getBoundingBox();
        if((bossBox.getMinX()>0 && bossBox.getMinX()<canvas.getWidth()) ||
           (bossBox.getMaxX()>0 && bossBox.getMaxX()<canvas.getWidth())){
            if(plr.getAction().equals("work")){ // increase
                love += increaseSpeed*dt/1e9;
            }
            else{ // decrease
                love -= decreaseSpeed*dt/1e9;
            }
            if(love<0) love=0;
            else if(love>1) love=1;
        }
    }
    public void draw(GraphicsContext gc){
        gc.setFill(borderColor);
        gc.fillRoundRect(x,y,w,h,h,h);
        gc.setFill(barColor);
        double len = love*(w-2*padding);
        gc.fillRoundRect(x+padding,y+padding,len,h-2*padding,Math.min(len,h-2*padding),Math.min(len,h-2*padding));
        gc.setFill(Color.WHITE);
        gc.setFont(Fonts.getDefault(0, FontWeight.BOLD));

        Text text = new Text("LOVE");
        gc.fillText("LOVE",x+w/2-text.getLayoutBounds().getWidth()/2,y+h/2+text.getLayoutBounds().getHeight()/2);
    }
}
