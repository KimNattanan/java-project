package scene;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.GamePanel;
import utils.Tools;


public class GameScene extends Scene {

    private long t0;
    private final ImageView train = new ImageView(new Image(String.valueOf(ClassLoader.getSystemResource("train/1.png"))));

    public GameScene(Stage stage){
        super(new Pane(),1000,600);
        Pane root = (Pane)getRoot();
        GamePanel gamePanel = new GamePanel(1000,600);
        gamePanel.setViewOrder(20);
        Tools.addMouseSparkle(root,root, Color.BLACK);

        train.setPreserveRatio(true);
        train.setFitHeight(root.getHeight());
        train.setViewOrder(-10);

        root.getChildren().add(gamePanel);
        gamePanel.requestFocus();

        trainOut(root);

        t0 = -1;
        AnimationTimer animation = new AnimationTimer() {
            @Override
            public void handle(long t1) {
                if(t0==-1){
                    t0 = t1;
                    return;
                }
                long dt = t1-t0;
                t0 = t1;
                try{
                    gamePanel.upd(dt);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
                gamePanel.paintComponent();
            }
        };
        animation.start();
    }

    private void trainOut(Pane pane){
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
