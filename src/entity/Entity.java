package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utils.AnimLoader;

public abstract class Entity {
    private double x,y,w,h,pivotX,pivotY;
    private String action;
    private AnimLoader curAnim;

    public Entity(double w,double h,double pivotX,double pivotY){
        this.w = w;
        this.h = h;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        action = "";
    }
    public Entity(double w,double h){ this(w,h,w/2,h/2); }
    public Entity(){ this(0,0,0,0); }

    public Image getFrame(){ return curAnim.getFrame(); }
    public void draw(GraphicsContext gc){
        gc.drawImage(getFrame(),x-pivotX, y-pivotY, w, h);
    }
    public void upd(long dt){
        curAnim.upd(dt);
    }
    public void setX(double x){ this.x=x; }
    public void setY(double y){ this.y=y; }
    public void setW(double w){ this.w=w; }
    public void setH(double h){ this.h=h; }
    public void setPivotX(double x){ pivotX=x; }
    public void setPivotY(double y){ pivotY=y; }
    public void setAction(String action){ this.action=action; }
    protected void setCurAnim(AnimLoader anim){ curAnim=anim; }
    public double getX(){ return x; }
    public double getY(){ return y; }
    public double getW(){ return w; }
    public double getH(){ return h; }
    public double getPivotX(){ return pivotX; }
    public double getPivotY(){ return pivotY; }
    public String getAction(){ return action; }
    protected AnimLoader getCurAnim(){ return curAnim; }
    public void translate(double dx,double dy){ x+=dx; y+=dy; }
    public void scale(double dw,double dh){ w+=dw; h+=dh; }
}
