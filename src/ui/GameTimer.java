package ui;

import background.Background;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.Fonts;

public class GameTimer {
    private final double canvasWidth;
    private long time;
    private final Background background;
    private final long bgTime = (long)(10e9);
    private final Color textColor = Color.WHITE;
    private final Color bgColor = Color.BLACK;
    private final double padding = 5;
    public GameTimer(Canvas canvas, Background bg){
        canvasWidth = canvas.getWidth();
        background = bg;
        background.insert("0","office/morning.jpg");
        background.insert("1","office/evening.jpg");
        background.insert("2","office/night_light_on.jpg");
        background.insert("3","office/night_light_off.jpg");
        background.setBg("0");
    }
    public void draw(GraphicsContext gc){
        String hr = String.valueOf((long)((time*21600.0/bgTime)/3600));

        gc.setFont(Fonts.getConsolas(5, FontWeight.BOLD));
        Text text = new Text(" Working Hours: "+hr+" ");
        text.setFont(gc.getFont());
        double w = text.getLayoutBounds().getWidth();
        double h = text.getLayoutBounds().getHeight();

        gc.setFill(bgColor);
        gc.fillRect(canvasWidth-w-2*padding,0,w+2*padding,h+2*padding);
        gc.setFill(textColor);
        gc.fillText(text.getText(),canvasWidth-w-padding,h);
    }
    public void upd(long dt){
        long bg0 = (time/bgTime)%4;
        time += dt;
        long bg1 = (time/bgTime)%4;
        if(bg0 != bg1) background.setBg(String.valueOf(bg1));
    }
}
