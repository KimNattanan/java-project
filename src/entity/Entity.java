package entity;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import utils.AnimLoader;

public abstract class Entity {
    private double x,y,w,h,pivotX,pivotY;
    private String action;
    private AnimLoader curAnim;
    private Image prevFrame;

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
    public double getX(){ return x; }

    public void setY(double y){ this.y=y; }
    public double getY(){ return y; }

    public void setW(double w){ this.w=w; }
    public double getW(){ return w; }

    public void setH(double h){ this.h=h; }
    public double getH(){ return h; }

    public void setPivotX(double x){ pivotX=x; }
    public double getPivotX(){ return pivotX; }

    public void setPivotY(double y){ pivotY=y; }
    public double getPivotY(){ return pivotY; }

    protected void setAction(String action, AnimLoader curAnim){ this.action=action; this.curAnim=curAnim; }
    public String getAction(){ return action; }
    protected AnimLoader getCurAnim(){ return curAnim; }

    public void translate(double dx,double dy){ x+=dx; y+=dy; }
    public void scaleAdd(double dw,double dh){ w+=dw; h+=dh; }
    public void scaleBy(double rw,double rh){ w*=rw; h*=rh; }
    public BoundingBox getBoundingBox(){ return new BoundingBox(x-pivotX,y-pivotY,w,h); }
}
