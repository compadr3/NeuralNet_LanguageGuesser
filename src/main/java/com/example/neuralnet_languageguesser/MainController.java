package com.example.neuralnet_languageguesser;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    Text guessedLanguage;
    @FXML
    TextField enteredText;
    @FXML
    Button buttonApply;
    @FXML
    MenuItem addTrainFiles, addLanguage;

    PerceptronNet perceptronNet = Learner.perceptronNet;
    //TODO: about
    //TODO: POPRAWKA - metoda initialize odpala sie zawsze przy kliknieciu settings
    @FXML
    protected void onApplyButtonClick(){
        String guessed = perceptronNet.checkOneLanguage(enteredText.getText());
        System.out.println("ODGADNIETO\n" + guessed);
        if(guessed == null || guessed.equals("")){
            guessedLanguage.setText("I couldn't guess a language...");
        } else {
            guessedLanguage.setText(guessed);
        }

    }
    @FXML
    protected void menuFiles(){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Window.class.getResource("AddTextFilesWindow.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 424, 236);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setOnCloseRequest(windowEvent -> {
            Learner.learn();
        });

        stage.setTitle("Settings");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void menuLanguage(){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Window.class.getResource("newLanguage.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 233, 144);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("New language");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void menuAbout(){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Window.class.getResource("about.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 424, 236);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("About");
        stage.setScene(scene);
        stage.show();
    }
}