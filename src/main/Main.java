package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scene.Scene1;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("start!");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("test title");
        Scene scene1 = new Scene1(stage);
        stage.setScene(scene1);
        stage.show();
    }
}