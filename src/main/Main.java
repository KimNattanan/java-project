package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scene.HomeScene;
import utils.AudioController;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AudioController.insert("homeBgm","bgm/listen_kyatto.mp3",0);
        AudioController.insert("playingBgm","bgm/the_epic.mp3",0);
        AudioController.insert("gameOverBgm","bgm/lullaby_kyatto.mp3",0);
        AudioController.insert("buttonClick","sfx/button_click.mp3",1);
        AudioController.insert("buttonClick2","sfx/button_click2.mp3",1);
        AudioController.insert("rewardSfx","sfx/reward.mp3",1);
        AudioController.insert("plrWork","player/sound/work.mp3",1);

        stage.setTitle("Office Survival");
        Scene scene = new HomeScene(stage);
        stage.setScene(scene);
        stage.show();
    }
}