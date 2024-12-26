package scene;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.GamePanel;
import utils.KeyHandler;
import utils.Tools;


public class GameScene extends Scene {
    private long t0 = -1;
    private final ImageView train = new ImageView(new Image(String.valueOf(ClassLoader.getSystemResource("train/1.png"))));

    public GameScene(Stage stage){
        super(new Pane(),1000,600);
        Pane root = (Pane)getRoot();
        GamePanel gamePanel = new GamePanel(1000,600);
        gamePanel.setViewOrder(20);
        Tools.addMouseSparkle(root,root, Color.BLACK);

        VBox pauseMenu = createPauseMenu(stage);
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
                GamePanel.setIsPause(!GamePanel.getIsPause());
                pauseMenu.setVisible(GamePanel.getIsPause());
            }
        });

        train.setPreserveRatio(true);
        train.setFitHeight(root.getHeight());
        train.setViewOrder(-10);
        trainOut(root);


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
                    if(!GamePanel.getIsPause()){
                        gamePanel.upd(dt);

                    }
                    else{
                        if(!pauseMenu.isVisible()) pauseMenu.setVisible(true);
                    }
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
                gamePanel.paintComponent();
            }
        };
        animation.start();
    }

    private VBox createPauseMenu(Stage stage) {
        VBox menu = new VBox(20);
        menu.setBackground(Background.fill(Color.rgb(0,0,0,0.3)));
        menu.setAlignment(Pos.CENTER);
        menu.setPrefSize(stage.getWidth(),stage.getHeight());

        Button exitBtn = createImageButton("ui/home_btn.png", "ui/home_btn_hover.png");
        exitBtn.setPrefSize(150,150);
        exitBtn.setOnAction(e->{
            stage.setScene(new HomeScene(stage));
        });

        menu.getChildren().add(exitBtn);
        return menu;
    }

    private StackPane createRewardsPane() {
        StackPane root = new StackPane();
        root.setPrefSize(this.getWidth(),this.getHeight());

        HBox btns = new HBox(20);
        btns.setPrefSize(this.getWidth(),this.getHeight());
        btns.setAlignment(Pos.CENTER);

        Button bento = createImageButton("ui/bento_btn.png", "ui/bento_btn_hover.png");
        bento.setPrefSize(200,200);
        Button coffee = createImageButton("ui/coffee_btn.png", "ui/coffee_btn_hover.png");
        coffee.setPrefSize(200,200);

        btns.getChildren().addAll(bento,coffee);

        ImageView bg = new ImageView(new Image(String.valueOf(ClassLoader.getSystemResource("background/rewards_bg.png"))));
        bg.setFitWidth(this.getWidth());
        bg.setFitHeight(this.getHeight());

        root.getChildren().addAll(bg,btns);
        return root;
    }
    private Button createImageButton(String defaultImg,String hoverImg){
        Button btn = new Button();
        btn.setStyle("-fx-background-color: transparent;" +
                "-fx-background-image: url("+defaultImg+");" +
                "-fx-background-size: 100% 100%;");
        btn.setOnMouseEntered(e->{
            btn.setStyle("-fx-background-color: transparent;" +
                    "-fx-background-image: url("+hoverImg+");" +
                    "-fx-background-size: 100% 100%;");
        });
        btn.setOnMouseExited(e->{
            btn.setStyle("-fx-background-color: transparent;" +
                    "-fx-background-image: url("+defaultImg+");" +
                    "-fx-background-size: 100% 100%;");
        });
        return btn;
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
