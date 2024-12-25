package ui;

import background.Background;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.Fonts;

public class GameTimer {
    private final double x,y;
    private long time;
    private final Background background;
    private final long bgTime = (long)(10e9);
    private final Color textColor = Color.WHITE;
    private final Color bgColor = Color.BLACK;
    private final double padding = 5;
    public GameTimer(double x, double y, Background bg){
        this.x = x;
        this.y = y;
        this.background = bg;
        background.insert("0","office/morning.jpg");
        background.insert("1","office/evening.jpg");
        background.insert("2","office/night_light_on.jpg");
        background.insert("3","office/night_light_off.jpg");
        background.setBg("0");
    }
    public void draw(GraphicsContext gc){
        double t = time*21600.0/bgTime;
        long _s = (long)(t)%60;
        t /= 60;
        long _m = (long)(t)%60;
        t /= 60;
        long _h = (long)(t);

        String sec = _s<10 ? "0"+String.valueOf(_s) : String.valueOf(_s);
        String min = _m<10 ? "0"+String.valueOf(_m) : String.valueOf(_m);
        String hr = String.valueOf(_h);

        gc.setFont(Fonts.getConsolas(4, FontWeight.BOLD));
        Text text = new Text(hr+":"+min+":"+sec);
        text.setFont(gc.getFont());
        double w = text.getLayoutBounds().getWidth();
        double h = text.getLayoutBounds().getHeight();

        gc.setFill(bgColor);
        gc.fillRect(x-w/2,y,w+2*padding,h+2*padding);
        gc.setFill(textColor);
        gc.fillText(text.getText(),x-w/2+padding,y+padding+h);
    }
    public void upd(long dt){
        long bg0 = (time/bgTime)%4;
        time += dt;
        long bg1 = (time/bgTime)%4;
        if(bg0 != bg1) background.setBg(String.valueOf(bg1));
    }
}
