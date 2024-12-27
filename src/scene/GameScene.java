package scene;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import ui.ImageButton;
import utils.Fonts;
import utils.GamePanel;
import utils.KeyHandler;
import utils.Tools;

public class GameScene extends Scene {
    private long t0 = -1;
    private boolean changingScene=false;
    private StackPane gameOverPane;

    public GameScene(Stage stage){
        super(new Pane(),1000,600);
        Pane root = (Pane)getRoot();
        GamePanel gamePanel = new GamePanel(1000,600);
        gamePanel.setViewOrder(20);
        Tools.addMouseSparkle(root,root, Color.BLACK);

        VBox pauseMenu = createPauseMenu(stage,root);
        pauseMenu.setViewOrder(15);
        pauseMenu.setVisible(false);

        StackPane rewardsPane = createRewardsPane();
        rewardsPane.setViewOrder(19);
        rewardsPane.setVisible(false);

        root.getChildren().addAll(gamePanel,rewardsPane,pauseMenu);
        gamePanel.requestFocus();

        this.setOnMouseMoved(e -> KeyHandler.setMousePos(e.getX(),e.getY()));
        this.setOnMouseDragged(e -> KeyHandler.setMousePos(e.getX(),e.getY()));
        this.addEventHandler(KeyEvent.KEY_RELEASED,e->{
            if(e.getCode()==KeyCode.ESCAPE){
                if(!GamePanel.getIsGameOver()) {
                    GamePanel.setIsPause(!GamePanel.getIsPause());
                    pauseMenu.setVisible(GamePanel.getIsPause());
                }
            }
        });

        Tools.trainOut(root);


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
                    if(GamePanel.getIsGameOver()){
                        if(gameOverPane == null){
                            gameOverPane = createGameOverPane(stage,root,gamePanel);
                            gameOverPane.setViewOrder(14);
                            root.getChildren().add(gameOverPane);
                        }
                    }
                    else{
                        if(gameOverPane.isVisible()) gameOverPane.setVisible(false);
                        if(GamePanel.getIsPause()){
                            if(!pauseMenu.isVisible()) pauseMenu.setVisible(true);
                        }
                        else{
                            if(pauseMenu.isVisible()) pauseMenu.setVisible(false);
                            if(GamePanel.getIsRewardable()){
                                if(!rewardsPane.isVisible()) rewardsPane.setVisible(true);
                            }
                            else{
                                if(rewardsPane.isVisible()) rewardsPane.setVisible(false);
                            }
                            gamePanel.upd(dt);
                        }
                    }
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
                gamePanel.paintComponent();
            }
        };
        animation.start();
    }

    private VBox createPauseMenu(Stage stage,Pane root) {
        VBox pane = new VBox(20);
        pane.setPrefSize(this.getWidth(),this.getHeight());
        pane.setAlignment(Pos.CENTER);
        pane.setBackground(Background.fill(Color.rgb(0,0,0,0.3)));

        Button home = createHomeButton(stage,root);
        home.setPrefSize(549,137);

        pane.getChildren().add(home);
        return pane;
    }

    private StackPane createRewardsPane() {
        StackPane pane = new StackPane();
        pane.setPrefSize(this.getWidth(),this.getHeight());
        pane.setAlignment(Pos.CENTER);

        HBox btns = new HBox(20);
        btns.setPrefSize(this.getWidth(),this.getHeight());
        btns.setAlignment(Pos.CENTER);

        Button bento = new ImageButton("ui/bento_btn.png", "ui/bento_btn_hover.png","ui/bento_btn_active.png");
        bento.setPrefSize(200,200);
        Button coffee = new ImageButton("ui/coffee_btn.png", "ui/coffee_btn_hover.png","ui/coffee_btn_active.png");
        coffee.setPrefSize(200,200);

        btns.getChildren().addAll(bento,coffee);

        ImageView bg = new ImageView(new Image(String.valueOf(ClassLoader.getSystemResource("background/rewards_bg.png"))));
        bg.setFitWidth(this.getWidth());
        bg.setFitHeight(this.getHeight());

        pane.getChildren().addAll(bg,btns);
        return pane;
    }

    private StackPane createGameOverPane(Stage stage,Pane root,GamePanel gamePanel){
        StackPane pane = new StackPane();
        pane.setPrefSize(this.getWidth(),this.getHeight());
        pane.setAlignment(Pos.CENTER_RIGHT);
        pane.setBackground(Background.fill(Color.rgb(0,0,0,0.5)));

        VBox menu = new VBox(20);
        menu.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        menu.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        menu.setAlignment(Pos.CENTER);
        menu.setBackground(Background.fill(Color.rgb(197,197,197)));
        menu.setPadding(new Insets(20));

        Label score = new Label("Score: "+String.valueOf(gamePanel.timer.getHours()));
        score.setFont(Fonts.getDefault(3, FontWeight.BOLD));
        score.setTextFill(Color.rgb(0,0,0));

        HBox btns = new HBox(20);

        Button home = createHomeButton(stage,root);
        home.setPrefSize(549*0.3,137*0.3);

        Button retry = createRetryButton(stage,root);
        retry.setPrefSize(549*0.3,137*0.3);

        btns.getChildren().addAll(home,retry);

        menu.getChildren().addAll(score,btns);

        pane.getChildren().add(menu);
        return pane;

    }


    private Button createHomeButton(Stage stage,Pane root){
        Button home = new ImageButton("ui/home_btn.png", "ui/home_btn_hover.png","ui/home_btn_active.png");
        home.setOnAction(e->{
            if(changingScene) return;
            changingScene = true;
            Tools.trainIn(root,()->{
                stage.setScene(new HomeScene(stage));
            });
        });
        return home;
    }

    private Button createRetryButton(Stage stage,Pane root){
        Button retry = new ImageButton("ui/retry_btn.png","ui/retry_btn_hover.png","ui/retry_btn_active.png");
        retry.setOnAction(e->{
            if(changingScene) return;
            changingScene = true;
            Tools.trainIn(root,()->{
                stage.setScene(new GameScene(stage));
            });
        });
        return retry;
    }
}
