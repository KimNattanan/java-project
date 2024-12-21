package scene;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.VideoTrack;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Fonts;

import java.util.Random;

public class MenuScene extends Scene {

    private boolean playClicked = false;

    public MenuScene(Stage stage) {
        super(new Pane(),1000,600);
        Pane root = (Pane)getRoot();
        root.setBackground(Background.fill(Color.rgb(155,155,125)));

        VBox btns = new VBox(20);
        btns.setPadding(new Insets(50,0,50,0));
        btns.setAlignment(Pos.TOP_CENTER);
        btns.setViewOrder(-1);

        Button playBtn = createPlayBtn(stage,root);
        Button tutorialBtn = createTutorialBtn(stage,root);
        Button settingsBtn = createSettingsBtn(stage,root);

        btns.getChildren().addAll(playBtn,tutorialBtn,settingsBtn);
        btns.setLayoutX(0);
        btns.setPrefWidth(root.getWidth());

        btns.heightProperty().addListener((_,_,val)->{
            btns.setLayoutY(root.getHeight()-val.doubleValue());
        });

        addMouseSparkle(root,root);

        root.getChildren().addAll(btns);
    }

    private void addMouseSparkle(Pane pane,Node node){
        pane.setOnMouseMoved(e->{
            addSparkle(pane,1,e.getSceneX(),e.getSceneY(),5,50,5,1000,Color.WHITE);
        });
        pane.setOnMouseDragged(e->{
            addSparkle(pane,1,e.getSceneX(),e.getSceneY(),5,50,5,1000,Color.WHITE);
        });
    }

    private Button createPlayBtn(Stage stage,Pane pane){
        Button btn = new Button("Go to Work!");
        btn.setFont(Fonts.getDefault(5, FontWeight.BOLD));
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: rgb(0,20,20)"
        );
        addTransition(btn,pane);

        FadeTransition fade = new FadeTransition(Duration.millis(1000),btn);
        fade.setToValue(0);
        fade.setOnFinished(e->{
            stage.setScene(new GameScene(stage));
        });

        ScaleTransition zoom = new ScaleTransition(Duration.millis(1000),btn);
        zoom.setByX(1.1);
        zoom.setByY(1.1);

        btn.setOnAction(e->{
            if(playClicked) return;
            playClicked = true;
            fade.playFromStart();
            zoom.playFromStart();
        });

        return btn;
    }
    private Button createTutorialBtn(Stage stage,Pane pane){
        Button btn = new Button("Tutorial");
        btn.setFont(Fonts.getDefault(5, FontWeight.NORMAL));
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: rgb(0,20,20)"
        );
        addTransition(btn,pane);
        return btn;
    }
    private Button createSettingsBtn(Stage stage,Pane pane){
        Button btn = new Button("Settings");
        btn.setFont(Fonts.getDefault(5, FontWeight.NORMAL));
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: rgb(0,20,20)"
        );
        addTransition(btn,pane);
        return btn;
    }

    private void addTransition(Node u,Pane pane){
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(500),u);
        scaleUp.setToX(1.4f);
        scaleUp.setToY(1.4f);
        scaleUp.setCycleCount(1);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(500),u);
        scaleDown.setToX(1f);
        scaleDown.setToY(1f);
        scaleDown.setCycleCount(1);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(700),u);
        fadeOut.setToValue(0.3);
        fadeOut.setAutoReverse(true);
        fadeOut.setCycleCount(9999);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(700),u);
        fadeIn.setToValue(1);

        u.setOnMouseEntered(e->{
            if(playClicked) return;
            scaleDown.stop();
            scaleUp.playFromStart();
            fadeOut.playFromStart();
        });
        u.setOnMouseExited(e->{
            if(playClicked) return;
            scaleUp.stop();
            scaleDown.playFromStart();
            fadeOut.stop();
            fadeIn.playFromStart();
        });
    }

    private void addSparkle(Pane pane,int n,double x,double y,double size,double range,double radius,double millis,Color color){
        while (n-- > 0){
            Circle u = new Circle(x-radius+Math.random()*2*radius,y-radius+Math.random()*2*radius,Math.random()*size,Color.rgb((int)(color.getRed()*255),(int)(color.getGreen()*255),(int)(color.getBlue()*255),Math.random()));
            u.setViewOrder(1);

            FadeTransition fade = new FadeTransition(Duration.millis(Math.random()*millis),u);
            fade.setToValue(0);
            fade.setOnFinished(event -> pane.getChildren().remove(u));

            TranslateTransition translate = new TranslateTransition(Duration.millis(millis),u);
            translate.setByX(-range+Math.random()*range*2);
            translate.setByY(-range+Math.random()*range*2);

            pane.getChildren().add(u);

            fade.playFromStart();
            translate.playFromStart();
        }
    }
}
