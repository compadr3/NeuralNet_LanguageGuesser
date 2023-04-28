package com.example.neuralnet_languageguesser;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;

public class AddLanguageController {
    @FXML
    TextArea languageNew;
    @FXML
    Text textInfo;
    String path = "C:\\Programowanie\\AI_projects\\NeuralNet_LanguageGuesser\\src";
    @FXML
    protected void addLanguage(){
        createDirectory(path + "\\Train\\" + languageNew.getText());
        createDirectory(path + "\\Test\\" + languageNew.getText());
    }

    public void createDirectory(String path){
        try {
            File theDir = new File(path);
            if (!theDir.exists()){
                theDir.mkdirs();
                System.out.println("Directory created: " + theDir.getName());
                textInfo.setText("Directory " + theDir.getName() + " created.");
            } else {
                System.out.println(languageNew.getText());
                System.out.println("Directory already exists.");
                textInfo.setText("Directory " + theDir.getName() + " already exists.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
