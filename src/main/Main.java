package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scene.MenuScene;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("start!");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Office Survival");
        Scene scene = new MenuScene(stage);
        stage.setScene(scene);
        stage.show();
    }
}