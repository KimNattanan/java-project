package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class Bar {
    private final double x,y,w,h,padding;
    private final Text title;
    private Color borderColor, barColor, titleColor;
    private double val; // portion
    private double speed; // portion per sec

    public Bar(double x, double y, double w, double h, double padding, String title, Font font){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.padding = padding;
        this.title = new Text(title);
        this.title.setFont(font);
    }

    public double getVal(){ return val; }
    public void setVal(double val){
        this.val=val;
        if(this.val < 0) this.val = 0;
        else if(this.val > 1) this.val = 1;
    }

    public double getX(){ return x; }
    public double getY(){ return y; }
    public double getW(){ return w; }
    public double getH(){ return h; }

    public double getSpeed(){ return speed; }
    public void setSpeed(double speed){ this.speed=speed; }

    public Color getBorderColor(){ return borderColor; }
    public void setBorderColor(Color color){ borderColor=color; }

    public Color getBarColor(){ return barColor; }
    public void setBarColor(Color color){ barColor=color; }

    public Color getTitleColor(){ return titleColor; }
    public void setTitleColor(Color color){ titleColor=color; }

    public void upd(long dt){
        setVal(getVal()+getSpeed()*dt/1e9);
    }

    private void fillFitRoundRect(GraphicsContext gc,double x,double y,double w,double h){
        gc.fillRoundRect(x,y,w,h,Math.min(w,h),Math.min(w,h));
    }
    public void draw(GraphicsContext gc){
        gc.setFill(borderColor);
        fillFitRoundRect(gc,x,y,w,h);

        if(getVal()!=0) {
            gc.save();
            gc.beginPath();
            gc.moveTo(x + padding, y + padding);
            gc.lineTo(x + padding + getVal() * (w - 2 * padding), y + padding);
            gc.lineTo(x + padding + getVal() * (w - 2 * padding), y + h - padding);
            gc.lineTo(x + padding, y + h - padding);
            gc.closePath();
            gc.clip();
            gc.setFill(barColor);
            fillFitRoundRect(gc, x + padding, y + padding, w - 2 * padding, h - 2 * padding);
            gc.restore();
        }

        gc.setFill(titleColor);
        gc.setFont(title.getFont());
        gc.fillText(title.getText(),x+w/2-title.getLayoutBounds().getWidth()/2,y+h/2+title.getLayoutBounds().getHeight()/2-2);
    }
}
