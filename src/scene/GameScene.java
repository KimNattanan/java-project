package scene;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import ui.ImageButton;
import utils.*;

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

        StackPane pauseMenu = createPauseMenu(stage,root);
        pauseMenu.setViewOrder(15);
        pauseMenu.setVisible(false);

        StackPane rewardsPane = createRewardsPane(gamePanel);
        rewardsPane.setViewOrder(19);
        rewardsPane.setVisible(false);

        root.getChildren().addAll(gamePanel,rewardsPane,pauseMenu);
        gamePanel.requestFocus();

        addKeyListener();
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
                        if(GamePanel.getIsRewardable()){
                            rewardsPane.setVisible(false);
                        }
                        if(gameOverPane == null){
                            gameOverPane = createGameOverPane(stage,root,gamePanel);
                            gameOverPane.setViewOrder(14);
                            VBox menu = (VBox)gameOverPane.lookup("#gameOverMenu");

                            double fadeTime = 2000;
                            double betweenTime = 10;
                            double translateTime = 500;


                            FadeTransition fade = new FadeTransition(Duration.millis(fadeTime),gameOverPane);
                            fade.setFromValue(0);
                            fade.setToValue(1);

                            FadeTransition fadeMenu = new FadeTransition(Duration.millis(translateTime+200),menu);
                            fadeMenu.setFromValue(0);
                            fadeMenu.setToValue(1);

                            TranslateTransition translateIn = new TranslateTransition(Duration.millis(translateTime),menu);
                            translateIn.setFromX(gamePanel.getWidth());
                            translateIn.setToX(menu.getLayoutX());

                            TranslateTransition translateOut = new TranslateTransition(Duration.millis(betweenTime),menu);
                            translateOut.setToX(gamePanel.getWidth());

                            fade.setOnFinished(e->{
                                translateOut.playFromStart();
                            });
                            translateOut.setOnFinished(e->{
                                menu.setVisible(true);
                                fadeMenu.playFromStart();
                                translateIn.playFromStart();
                            });

                            menu.setVisible(false);
                            gameOverPane.setOpacity(0);
                            root.getChildren().add(gameOverPane);
                            fade.playFromStart();
                        }
                    }
                    else{
                        if(GamePanel.getIsPause()){
                            if(!pauseMenu.isVisible()) pauseMenu.setVisible(true);
                        }
                        else{
                            if(pauseMenu.isVisible()) pauseMenu.setVisible(false);
                            if(GamePanel.getIsRewardable()){
                                if(!rewardsPane.isVisible()){
                                    rewardsPane.setVisible(true);
                                }
                            }
                            else{
                                if(rewardsPane.isVisible()){
                                    rewardsPane.setVisible(false);
                                }
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
        stage.sceneProperty().addListener((e,prev,cur)->{
            if(prev==this) animation.stop();
        });
        animation.start();
    }

    public void addKeyListener() {
        this.addEventHandler(KeyEvent.KEY_PRESSED, e->KeyHandler.setKeyPressed(e.getCode(),true));
        this.addEventHandler(KeyEvent.KEY_RELEASED, e->KeyHandler.setKeyPressed(e.getCode(),false));
        this.setOnMouseMoved(e -> KeyHandler.setMousePos(e.getX(),e.getY()));
        this.setOnMouseDragged(e -> KeyHandler.setMousePos(e.getX(),e.getY()));
    }

    private StackPane createPauseMenu(Stage stage,Pane root) {
        StackPane pane = new StackPane();
        pane.setPrefSize(this.getWidth(),this.getHeight());
        pane.setAlignment(Pos.CENTER);
        pane.setBackground(Background.fill(Color.rgb(0,0,0,0.3)));

        VBox menu = Tools.createWindowUI(true,"pause");
        Button closeBtn = (Button)menu.lookup("#closeBtn");
        closeBtn.setOnAction(e -> GamePanel.setIsPause(false));

        VBox body = new VBox(20);
        body.setPadding(new Insets(0,20,20,20));
        body.setAlignment(Pos.CENTER);

        Pane audioPane = AudioController.createPane();

        Button home = createHomeButton(stage,root);
        home.setPrefSize(549*0.3,137*0.3);

        body.getChildren().addAll(audioPane,home);
        menu.getChildren().add(body);
        pane.getChildren().add(menu);
        return pane;
    }

    private StackPane createRewardsPane(GamePanel gamePanel) {
        StackPane pane = new StackPane();
        pane.setPrefSize(this.getWidth(),this.getHeight());
        pane.setAlignment(Pos.CENTER);

        VBox menu = Tools.createWindowUI(false);
        menu.setPadding(new Insets(60,40,40,40));

        Text text = new Text("Nice Work!");
        text.setFont(Fonts.getConsolas(7, FontWeight.BOLD));
        text.setUnderline(true);

        FillTransition textAnim = new FillTransition(Duration.millis(500),text);
        textAnim.setFromValue(Color.rgb(200,0,0));
        textAnim.setToValue(Color.rgb(255,0,0));
        textAnim.setInterpolator(Interpolator.EASE_IN);
        textAnim.setAutoReverse(true);
        textAnim.setCycleCount(Animation.INDEFINITE);
        textAnim.playFromStart();

        HBox btns = new HBox(40);
        btns.setAlignment(Pos.CENTER);

        Button bento = new ImageButton("ui/bento_btn.png", "ui/bento_btn_hover.png","ui/bento_btn_active.png");
        bento.setPrefSize(200,200);
        bento.setTranslateY(15);
        bento.setOnMouseClicked(e->{
            if(e.getButton() != MouseButton.PRIMARY) return;
            gamePanel.energyBar.eatBento();
            gamePanel.loveBar.setVal(0);
            GamePanel.setIsRewardable(false);
        });
        Button coffee = new ImageButton("ui/coffee_btn.png", "ui/coffee_btn_hover.png","ui/coffee_btn_active.png");
        coffee.setPrefSize(200,200);
        coffee.setOnMouseClicked(e->{
            if(e.getButton() != MouseButton.PRIMARY) return;
            gamePanel.sleepBar.drinkCoffee();
            gamePanel.loveBar.setVal(0);
            GamePanel.setIsRewardable(false);
        });

        btns.getChildren().addAll(bento,coffee);

        menu.getChildren().addAll(text,btns);

        ImageView bg = new ImageView(new Image(String.valueOf(ClassLoader.getSystemResource("background/rewards_bg.png"))));
        bg.setFitWidth(this.getWidth());
        bg.setFitHeight(this.getHeight());

        pane.getChildren().addAll(bg,menu);
        return pane;
    }

    private StackPane createGameOverPane(Stage stage,Pane root,GamePanel gamePanel){
        StackPane pane = new StackPane();
        pane.setPrefSize(this.getWidth(),this.getHeight());
        pane.setAlignment(Pos.CENTER_RIGHT);
        pane.setBackground(Background.fill(Color.rgb(0,0,0,0.7)));
        pane.setPadding(new Insets(10));

        VBox menu = Tools.createWindowUI(false);
        menu.setPadding(new Insets(20));
        menu.setId("gameOverMenu");

        Text score = new Text("Score: "+String.valueOf(gamePanel.timer.getHours()));
        score.setFont(Fonts.getConsolas(3, FontWeight.BOLD));
        score.setFill(Color.rgb(0,0,0));

        FillTransition scoreAnim = new FillTransition(Duration.millis(500),score);
        scoreAnim.setToValue(Color.rgb(255,0,115));
        scoreAnim.setAutoReverse(true);
        scoreAnim.setCycleCount(Animation.INDEFINITE);
        scoreAnim.playFromStart();

        HBox btns = new HBox(20);

        Button home = createHomeButton(stage,root);
        home.setPrefSize(549*0.3,137*0.3);

        Button retry = createRetryButton(stage,root);
        retry.setPrefSize(549*0.3,137*0.3);

        btns.getChildren().addAll(retry,home);

        menu.getChildren().addAll(score,btns);

        pane.getChildren().add(menu);
        return pane;
    }

    private Button createHomeButton(Stage stage,Pane root){
        Button home = new ImageButton("ui/home_btn.png", "ui/home_btn_hover.png","ui/home_btn_active.png");
        home.setCursor(Cursor.HAND);
        home.setOnMouseClicked(e->{
            if(changingScene || e.getButton() != MouseButton.PRIMARY) return;
            changingScene = true;
            Tools.trainIn(root,()->{
                stage.setScene(new HomeScene(stage));
            });
        });
        return home;
    }

    private Button createRetryButton(Stage stage,Pane root){
        Button retry = new ImageButton("ui/retry_btn.png","ui/retry_btn_hover.png","ui/retry_btn_active.png");
        retry.setCursor(Cursor.HAND);
        retry.setOnMouseClicked(e->{
            if(changingScene  || e.getButton() != MouseButton.PRIMARY) return;
            changingScene = true;
            Tools.trainIn(root,()->{
                stage.setScene(new GameScene(stage));
            });
        });
        return retry;
    }
}
