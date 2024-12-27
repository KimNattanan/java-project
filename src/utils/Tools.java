package utils;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Tools {
    public static double mathRnd(double mn,double mx){
        return mn+Math.random()*(mx-mn);
    }
    public static void addMouseSparkle(Pane pane, Node node, Color color){
        node.setOnMouseMoved(e->{
            addSparkle(pane,1,e.getSceneX(),e.getSceneY(),5,50,5,1000,color);
        });
        node.setOnMouseDragged(e->{
            addSparkle(pane,1,e.getSceneX(),e.getSceneY(),5,50,5,1000,color);
        });
    }
    public static void addSparkle(Pane pane, int n, double x, double y, double size, double range, double radius, double millis, Color color){
        while (n-- > 0){
            Circle u = new Circle(x+mathRnd(-radius,radius),y+mathRnd(-radius,radius),mathRnd(0,size),Color.rgb((int)(color.getRed()*255),(int)(color.getGreen()*255),(int)(color.getBlue()*255),mathRnd(0,1)));
            u.setViewOrder(10);

            FadeTransition fade = new FadeTransition(Duration.millis(mathRnd(0,millis)),u);
            fade.setToValue(0);
            fade.setOnFinished(e -> pane.getChildren().remove(u));

            TranslateTransition translate = new TranslateTransition(Duration.millis(millis),u);
            translate.setByX(mathRnd(-range,range));
            translate.setByY(mathRnd(-range,range));

            pane.getChildren().add(u);

            fade.playFromStart();
            translate.playFromStart();
        }
    }

    public static void trainIn(Pane pane,Runnable runnable){
        ImageView train = new ImageView(new Image(String.valueOf(ClassLoader.getSystemResource("train/1.png"))));
        train.setPreserveRatio(true);
        train.setFitHeight(pane.getHeight());
        train.setViewOrder(-10);
        double w = train.getLayoutBounds().getWidth();
        train.setLayoutY(0);
        train.setLayoutX(-w);
        pane.getChildren().add(train);
        TranslateTransition translate = new TranslateTransition(Duration.millis(1000),train);
        translate.setByX((w+pane.getWidth())/2);
        translate.playFromStart();
        translate.setOnFinished(e->runnable.run());
    }
    public static void trainOut(Pane pane){
        ImageView train = new ImageView(new Image(String.valueOf(ClassLoader.getSystemResource("train/1.png"))));
        train.setPreserveRatio(true);
        train.setFitHeight(pane.getHeight());
        train.setViewOrder(-10);
        double w = train.getLayoutBounds().getWidth();
        train.setLayoutY(0);
        train.setLayoutX((pane.getWidth()-w)/2);
        pane.getChildren().add(train);
        TranslateTransition translate = new TranslateTransition(Duration.millis(1000),train);
        translate.setByX((w+pane.getWidth())/2);
        translate.playFromStart();
        translate.setOnFinished(e->{
            pane.getChildren().remove(train);
        });
    }
}
