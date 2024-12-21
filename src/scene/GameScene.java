package scene;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.GamePanel;

public class GameScene extends Scene {
    private long t0;
    public GameScene(Stage stage){
        super(new StackPane(),600,600);
        StackPane root = (StackPane)getRoot();
        GamePanel gamePanel = new GamePanel(600,600);
        root.getChildren().add(gamePanel);
        gamePanel.requestFocus();

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
}
