package com.example.neuralnet_languageguesser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Window extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Window.class.getResource("mainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 401);
        stage.setTitle("Language Guesser");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Learner.learn();
        launch();
    }
}