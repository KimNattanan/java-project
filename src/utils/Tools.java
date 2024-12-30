package utils;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ui.ImageButton;

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
            u.setViewOrder(-10);
            u.setMouseTransparent(true);

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

    public static VBox createWindowUI(){
        VBox window = new VBox(10);
        window.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        window.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        window.setAlignment(Pos.CENTER);
        window.setBorder(Border.stroke(Color.rgb(47,47,97)));

        final Animation bgAnim = new Transition() {
            {
                setCycleDuration(Duration.millis(500));
                setInterpolator(Interpolator.EASE_IN);
            }

            @Override
            protected void interpolate(double frac) {
                window.setBackground(Background.fill(Color.rgb(223,222,234,0.3+0.4*(1-frac))));
            }
        };
        bgAnim.setCycleCount(Animation.INDEFINITE);
        bgAnim.setAutoReverse(true);
        bgAnim.play();

        return window;
    }
    public static VBox createWindowUI(boolean usingTab,String title){
        VBox window = createWindowUI();
        if(usingTab) {
            StackPane tab = new StackPane();
            tab.setAlignment(Pos.CENTER_RIGHT);
            tab.setMinHeight(Region.USE_PREF_SIZE);
            tab.setMaxHeight(Region.USE_PREF_SIZE);

            ImageButton closeBtn = new ImageButton("ui/close.png", "ui/close_hover.png", "ui/close_active.png");
            closeBtn.setPrefSize(862 * 0.05, 720 * 0.05);
            closeBtn.setId("closeBtn");
            closeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,e->{
                AudioController.play("buttonClick2",1,0);
            });

            VBox titleBox = new VBox();
            titleBox.setAlignment(Pos.CENTER);
            Text text = new Text(title);
            text.setFont(Fonts.getConsolas(1, FontWeight.BOLD));
            text.setFill(Color.rgb(0,0,0));
            titleBox.getChildren().add(text);

            tab.getChildren().addAll(titleBox,closeBtn);
            HBox.setHgrow(titleBox,Priority.ALWAYS);
            window.getChildren().add(tab);
            window.setMinWidth(text.getLayoutBounds().getWidth()+2*862*0.05);
        }
        return window;
    }
    public static VBox createWindowUI(boolean usingTab){
        return createWindowUI(usingTab,"");
    }
}
