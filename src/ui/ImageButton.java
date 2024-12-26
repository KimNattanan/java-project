package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import utils.KeyHandler;

public abstract class ImageButton {
    private final Image img;
    private double x,y,w,h,arcW,arcH,padding;
    private Color bgColor,borderColor;
    private Color bgColorHover,borderColorHover;

    public ImageButton(String path){
        img = new Image(String.valueOf(ClassLoader.getSystemResource(path)));

    }

    public double getX(){ return x; }
    public void setX(double x){ this.x=x; }

    public double getY(){ return y; }
    public void setY(double y){ this.y=y; }

    public double getW(){ return w; }
    public void setW(double w){ this.w=w; }

    public double getH(){ return h; }
    public void setH(double h){ this.h=h; }

    public double getArcW(){ return arcW; }
    public void setArcW(double arcW){ this.arcW=arcW; }

    public double getArcH(){ return arcH; }
    public void setArcH(double arcH){ this.arcH=arcH; }

    public double getPadding(){ return padding; }
    public void setPadding(double padding){ this.padding=padding; }

    public Color getBgColor(){ return bgColor; }
    public void setBgColor(Color bgColor){ this.bgColor=bgColor; }

    public Color getBorderColor(){ return borderColor; }
    public void setBorderColor(Color borderColor){ this.borderColor=borderColor; }

    public void draw(GraphicsContext gc){
        boolean hover = ( KeyHandler.getMouseX() >= getX() && KeyHandler.getMouseX() <= getX()+getW() &&
                          KeyHandler.getMouseY() >= getY() && KeyHandler.getMouseY() <= getY()+getH() );
        gc.setFill(hover ? borderColorHover : borderColor);
        gc.fillRoundRect(x, y, w, h, arcW, arcH);
        gc.setFill(hover ? bgColorHover : bgColor);
        gc.fillRoundRect(x + padding, y + padding, w - 2 * padding, h - 2 * padding, arcW - 2 * padding, arcH - 2 * padding);
        gc.drawImage(img, x + padding, y + padding, w - 2 * padding, h - 2 * padding);
    }
}
