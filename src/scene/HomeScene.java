package scene;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import ui.ImageButton;
import utils.AudioController;
import utils.Fonts;
import utils.Tools;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;

public class HomeScene extends Scene {

    private boolean playClicked = false;
    private final VBox settingsPane = Tools.createWindowUI(true,"Settings");
    private final VBox tutorialPane = Tools.createWindowUI(true,"Tutorial");
    private final VBox creditsPane = Tools.createWindowUI(true,"Credits");
    private int curTutorialPage = 0;
    private final Text[] tutorialPages = {
    new Text("" +
            "Press 'W' to work and 'Esc' to pause."
    ),
    new Text("" +
            " - Energy: Will gradually decrease and will decrease faster while working. You will die if this runs out.\n" +
            " - Awakeness: Will gradually decrease. You will be forced to sleep if this runs out.\n" +
            " - Merit: While the manager is walking by, this will increase if you are working and will decrease otherwise."
    ),
    new Text("" +
            "If the merit is full, you will have to choose 1 reward from your manager.\n" +
            "There are 2 options as follows:\n" +
            " - Manager's Bento: Your energy will become max again from eating this bento.\n" +
            " - Coffee: You will receive 70% of your max awakeness and 30% of your max energy from drinking this."
    )
    };

    public HomeScene(Stage stage) {
        super(new Pane(),1000,600);
        Pane root = (Pane)getRoot();
        root.setBackground(Background.fill(Color.rgb(155,155,125)));

        VBox btns = new VBox(20);
        btns.setPadding(new Insets(50,0,50,0));
        btns.setAlignment(Pos.TOP_CENTER);

        Button playBtn = createPlayBtn(stage,root);
        Button tutorialBtn = createTutorialBtn();
        Button settingsBtn = createSettingsBtn();
        Button creditsBtn = createCreditsBtn();

        HBox setNcreBtns = new HBox(20);
        setNcreBtns.setAlignment(Pos.CENTER);
        setNcreBtns.getChildren().addAll(settingsBtn,creditsBtn);

        btns.getChildren().addAll(playBtn,tutorialBtn,setNcreBtns);
        btns.setLayoutX(0);
        btns.setPrefWidth(root.getWidth());

        btns.heightProperty().addListener((_,_,val)->{
            btns.setLayoutY(root.getHeight()-val.doubleValue());
        });

        createTutorialPane();
        tutorialPane.setLayoutY(20);
        tutorialPane.widthProperty().addListener((_,_,val)->{
            tutorialPane.setLayoutX((root.getWidth()-val.doubleValue())/2);
        });

        createSettingsPane();
        settingsPane.setLayoutY(20);
        settingsPane.widthProperty().addListener((_,_,val)->{
            settingsPane.setLayoutX((root.getWidth()-val.doubleValue())/2);
        });

        createCreditsPane();
        creditsPane.setLayoutY(20);
        creditsPane.widthProperty().addListener((_,_,val)->{
            creditsPane.setLayoutX((root.getWidth()-val.doubleValue())/2);
        });

        root.getChildren().addAll(btns,settingsPane,tutorialPane,creditsPane);

        Tools.trainOut(root);
        Tools.addMouseSparkle(root,root,Color.WHITE);

        AudioController.stopAll();
        AudioController.play("homeBgm",MediaPlayer.INDEFINITE,2000);
    }

    private void createTutorialPane(){
        tutorialPane.setVisible(false);
        tutorialPane.setViewOrder(-1);
        Button closeBtn = (Button)tutorialPane.lookup("#closeBtn");
        closeBtn.setOnAction(e->tutorialPane.setVisible(false));

        VBox body = new VBox();
        body.setAlignment(Pos.TOP_CENTER);
        body.setPadding(new Insets(20,20,20,20));
        body.setMinWidth(-1.0/0.0);
        body.setMaxWidth(-1.0/0.0);

        for(Text page:tutorialPages) {
            page.setFont(Fonts.getDefault(2, FontWeight.NORMAL));
            page.setFill(Color.rgb(0, 0, 0));
            page.setLineSpacing(10);
        }

        HBox btns = new HBox();
        btns.setAlignment(Pos.BOTTOM_CENTER);

        ImageButton lBtn = new ImageButton("ui/black_btn.png","ui/black_btn_hover.png","ui/black_btn_active.png");
        lBtn.setTextFill(Color.rgb(255,255,255));
        lBtn.setFont(Fonts.getConsolas(1,FontWeight.BOLD));
        lBtn.setText("<");

        ImageButton rBtn = new ImageButton("ui/black_btn.png","ui/black_btn_hover.png","ui/black_btn_active.png");
        rBtn.setTextFill(Color.rgb(255,255,255));
        rBtn.setFont(Fonts.getConsolas(1,FontWeight.BOLD));
        rBtn.setText(">");

        btns.widthProperty().addListener((_,_,val)->{
            lBtn.setMinWidth(val.doubleValue()/2);
            rBtn.setMinWidth(val.doubleValue()/2);
        });

        lBtn.setVisible(false);

        lBtn.setOnAction(e->{
            AudioController.play("buttonClick2",1,0);
            body.getChildren().remove(tutorialPages[curTutorialPage]);
            --curTutorialPage;
            if(curTutorialPage==0) lBtn.setVisible(false);
            if(curTutorialPage!=2) rBtn.setVisible(true);
            body.getChildren().add(tutorialPages[curTutorialPage]);
        });
        rBtn.setOnAction(e->{
            AudioController.play("buttonClick2",1,0);
            body.getChildren().remove(tutorialPages[curTutorialPage]);
            ++curTutorialPage;
            if(curTutorialPage!=0) lBtn.setVisible(true);
            if(curTutorialPage==2) rBtn.setVisible(false);
            body.getChildren().add(tutorialPages[curTutorialPage]);
        });

        body.widthProperty().addListener((_,_,val)->{
            tutorialPane.setMaxWidth(Math.max(tutorialPane.getMinWidth(),val.doubleValue()));
            btns.setMinWidth(tutorialPane.getMaxWidth());
            btns.setMaxWidth(tutorialPane.getMaxWidth());
        });

        btns.getChildren().addAll(lBtn,rBtn);
        body.getChildren().addAll(tutorialPages[curTutorialPage]);
        tutorialPane.getChildren().addAll(body,btns);
        HBox.setHgrow(btns,Priority.ALWAYS);
    }

    private void createSettingsPane(){
        settingsPane.setVisible(false);
        settingsPane.setViewOrder(-1);
        Button closeBtn = (Button)settingsPane.lookup("#closeBtn");
        closeBtn.setOnAction(e->settingsPane.setVisible(false));

        Pane audioPane = AudioController.createPane();
        audioPane.setPadding(new Insets(0,100,20,100));
        settingsPane.getChildren().addAll(audioPane);
    }

    private void createCreditsPane(){
        creditsPane.setVisible(false);
        creditsPane.setViewOrder(-1);
        Button closeBtn = (Button)creditsPane.lookup("#closeBtn");
        closeBtn.setOnAction(e->creditsPane.setVisible(false));

        VBox body = new VBox(20);
        body.setPadding(new Insets(0,20,20,20));

        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER_LEFT);
        HBox row2 = new HBox();
        row2.setAlignment(Pos.CENTER_LEFT);

        Text bgmText = new Text("BGM:  ");
        bgmText.setFont(Fonts.getDefault(2,FontWeight.NORMAL));
        bgmText.setFill(Color.BLACK);

        Text illustText = new Text("Illust:  ");
        illustText.setFont(Fonts.getDefault(2,FontWeight.NORMAL));
        illustText.setFill(Color.BLACK);

        Button kyattoLink = new Button("KyattoWorks");
        kyattoLink.setFont(Fonts.getDefault(2,FontWeight.NORMAL));
        kyattoLink.setTextFill(Color.rgb(0,77,222));
        kyattoLink.setCursor(Cursor.HAND);
        kyattoLink.setStyle("-fx-background-color: transparent");
        kyattoLink.setPadding(new Insets(0));
        kyattoLink.setOnAction(e->{
            try {
                URI uri = new URI("https://safuworks.com");
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(uri);
                } else {
                    System.out.println("Desktop browsing is not supported on this platform.");
                }
            } catch (Exception er) {
                System.out.println(er);
            }
        });
        kyattoLink.setOnMouseEntered(e->kyattoLink.setUnderline(true));
        kyattoLink.setOnMouseExited(e->kyattoLink.setUnderline(false));

        Button hutabaLink = new Button("HUTABA free illust");
        hutabaLink.setFont(Fonts.getDefault(2,FontWeight.NORMAL));
        hutabaLink.setTextFill(Color.rgb(0,77,222));
        hutabaLink.setCursor(Cursor.HAND);
        hutabaLink.setStyle("-fx-background-color: transparent");
        hutabaLink.setPadding(new Insets(0));
        hutabaLink.setOnAction(e->{
            try {
                URI uri = new URI("https://eiyoushi-hutaba.com/");
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(uri);
                } else {
                    System.out.println("Desktop browsing is not supported on this platform.");
                }
            } catch (Exception er) {
                System.out.println(er);
            }
        });
        hutabaLink.setOnMouseEntered(e->hutabaLink.setUnderline(true));
        hutabaLink.setOnMouseExited(e->hutabaLink.setUnderline(false));

        row1.getChildren().addAll(bgmText,kyattoLink);
        row2.getChildren().addAll(illustText,hutabaLink);
        body.getChildren().addAll(row1,row2);

        creditsPane.getChildren().add(body);
    }

    private Button createPlayBtn(Stage stage,Pane pane){
        Button btn = new Button("Go to Work!");
        btn.setFont(Fonts.getDefault(6, FontWeight.BOLD));
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: rgb(0,20,20)"
        );
        addTransition(btn);

        FadeTransition fade = new FadeTransition(Duration.millis(1000),btn);
        fade.setToValue(0);

        ScaleTransition zoom = new ScaleTransition(Duration.millis(1000),btn);
        zoom.setByX(50);
        zoom.setByY(50);

        btn.setOnAction(e->{
            if(playClicked) return;
            playClicked = true;
            AudioController.stop("homeBgm",2000);
            AudioController.play("buttonClick",1,0);
            fade.playFromStart();
            zoom.playFromStart();
            Tools.trainIn(pane,()->{
                stage.setScene(new GameScene(stage));
            });
        });

        return btn;
    }
    private Button createTutorialBtn(){
        Button btn = new Button("Tutorial");
        btn.setFont(Fonts.getDefault(6, FontWeight.NORMAL));
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: rgb(0,20,20)"
        );
        addTransition(btn);

        btn.setOnAction(e->{
            AudioController.play("buttonClick2",1,0);
            if(settingsPane.isVisible()) settingsPane.setVisible(false);
            if(creditsPane.isVisible()) creditsPane.setVisible(false);
            tutorialPane.setVisible(!tutorialPane.isVisible());
        });

        return btn;
    }
    private Button createSettingsBtn(){
        Button btn = new Button("Settings");
        btn.setFont(Fonts.getDefault(5, FontWeight.NORMAL));
        btn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: rgb(0,20,20)"
        );
        addTransition(btn);

        btn.setOnAction(e->{
            AudioController.play("buttonClick2",1,0);
            if(tutorialPane.isVisible()) tutorialPane.setVisible(false);
            if(creditsPane.isVisible()) creditsPane.setVisible(false);
            settingsPane.setVisible(!settingsPane.isVisible());
        });

        return btn;
    }
    private Button createCreditsBtn(){
        Button btn = new Button("Credits");
        btn.setFont(Fonts.getDefault(5, FontWeight.NORMAL));
        btn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: rgb(0,20,20)"
        );
        addTransition(btn);

        btn.setOnAction(e->{
            AudioController.play("buttonClick2",1,0);
            if(tutorialPane.isVisible()) tutorialPane.setVisible(false);
            if(settingsPane.isVisible()) settingsPane.setVisible(false);
            creditsPane.setVisible(!creditsPane.isVisible());
        });

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
}
