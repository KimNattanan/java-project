package scene;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Fonts;

import static utils.Tools.addMouseSparkle;

public class MenuScene extends Scene {

    private boolean playClicked = false;
    private final ImageView train = new ImageView(new Image(String.valueOf(ClassLoader.getSystemResource("train/1.png"))));

    public MenuScene(Stage stage) {
        super(new Pane(),1000,600);
        Pane root = (Pane)getRoot();
        root.setBackground(Background.fill(Color.rgb(155,155,125)));

        VBox btns = new VBox(20);
        btns.setPadding(new Insets(50,0,50,0));
        btns.setAlignment(Pos.TOP_CENTER);
        btns.setViewOrder(-1);

        Button playBtn = createPlayBtn(stage,root);
        Button tutorialBtn = createTutorialBtn(stage);
        Button settingsBtn = createSettingsBtn(stage);

        btns.getChildren().addAll(playBtn,tutorialBtn,settingsBtn);
        btns.setLayoutX(0);
        btns.setPrefWidth(root.getWidth());

        btns.heightProperty().addListener((_,_,val)->{
            btns.setLayoutY(root.getHeight()-val.doubleValue());
        });

        addMouseSparkle(root,root);

        train.setPreserveRatio(true);
        train.setFitHeight(root.getHeight());
        train.setViewOrder(-10);

        root.getChildren().addAll(btns);
    }

    private Button createPlayBtn(Stage stage,Pane pane){
        Button btn = new Button("Go to Work!");
        btn.setFont(Fonts.getDefault(5, FontWeight.BOLD));
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: rgb(0,20,20)"
        );
        addTransition(btn);

        FadeTransition fade = new FadeTransition(Duration.millis(1000),btn);
        fade.setToValue(0);
        fade.setOnFinished(e->{
            stage.setScene(new GameScene(stage));
        });

        ScaleTransition zoom = new ScaleTransition(Duration.millis(1000),btn);
        zoom.setByX(50);
        zoom.setByY(50);

        btn.setOnAction(e->{
            if(playClicked) return;
            playClicked = true;
            fade.playFromStart();
            zoom.playFromStart();
            trainIn(pane);
        });

        return btn;
    }
    private Button createTutorialBtn(Stage stage){
        Button btn = new Button("Tutorial");
        btn.setFont(Fonts.getDefault(5, FontWeight.NORMAL));
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: rgb(0,20,20)"
        );
        addTransition(btn);
        return btn;
    }
    private Button createSettingsBtn(Stage stage){
        Button btn = new Button("Settings");
        btn.setFont(Fonts.getDefault(5, FontWeight.NORMAL));
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: rgb(0,20,20)"
        );
        addTransition(btn);
        return btn;
    }

    private void addTransition(Node u){
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(500),u);
        scaleUp.setToX(1.4f);
        scaleUp.setToY(1.4f);
        scaleUp.setCycleCount(1);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(500),u);
        scaleDown.setToX(1f);
        scaleDown.setToY(1f);
        scaleDown.setCycleCount(1);

        FadeTransition fade = new FadeTransition(Duration.millis(700),u);
        fade.setToValue(0.3);
        fade.setAutoReverse(true);
        fade.setCycleCount(9999);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(700),u);
        fadeIn.setToValue(1);

        u.setOnMouseEntered(e->{
            scaleDown.stop();
            if(playClicked){
                scaleUp.stop();
                fade.stop();
                return;
            };
            scaleUp.playFromStart();
            fade.playFromStart();
        });
        u.setOnMouseExited(e->{
            scaleUp.stop();
            fade.stop();
            if(playClicked){
                scaleDown.stop();
                fadeIn.stop();
                return;
            }
            scaleDown.playFromStart();
            fadeIn.playFromStart();
        });
    }
    private void trainIn(Pane pane){
        double w = train.getLayoutBounds().getWidth();
        train.setLayoutY(0);
        train.setLayoutX(-w);
        pane.getChildren().add(train);
        TranslateTransition translate = new TranslateTransition(Duration.millis(1000),train);
        translate.setByX((w+pane.getWidth())/2);
        translate.playFromStart();
    }
}
