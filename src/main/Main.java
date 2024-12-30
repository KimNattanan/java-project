package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scene.HomeScene;
import utils.AudioController;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("start!");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AudioController.insert("buttonClick","sfx/button_click.mp3",1);
        AudioController.insert("buttonClick2","sfx/button_click2.mp3",1);
        AudioController.insert("homeBgm","bgm/listen_kyatto.mp3",0);
        AudioController.insert("playingBgm","bgm/moonlight_lament.mp3",0);
        AudioController.insert("gameOverBgm","bgm/lullaby_kyatto.mp3",0);
        AudioController.insert("rewardSfx","sfx/reward.mp3",0);

        stage.setTitle("Office Survival");
        Scene scene = new HomeScene(stage);
        stage.setScene(scene);
        stage.show();
    }
}