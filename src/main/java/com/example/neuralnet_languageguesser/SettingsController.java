package com.example.neuralnet_languageguesser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    ChoiceBox<String> supportedLanguages;
    @FXML
    TextArea textAreaTrain;
    @FXML
    TextArea textAreaTest;
    @FXML
    Text textInfo;
    String trainDirectory = "C:\\Programowanie\\AI_projects\\NeuralNet_LanguageGuesser\\src\\Train";
    String testDirectory = "C:\\Programowanie\\AI_projects\\NeuralNet_LanguageGuesser\\src\\Test";
    @FXML
    protected void addTrainTextFile() throws IOException {
        System.out.println(supportedLanguages.getValue());
        try {
            create(trainDirectory, textAreaTrain, "Train");
        } catch (NullPointerException ne) {
            textInfo.setText("Sorry, I could not create text file. You have to choose a language.");
            ne.printStackTrace();
        }

        textAreaTrain.setText("");
    }
    @FXML
    protected void addTestTextFile() throws IOException {
        System.out.println(supportedLanguages.getValue());
        create(testDirectory, textAreaTest, "Test");
        textAreaTest.setText("");
    }
    public void create(String directory, TextArea textArea, String trainOrTest) throws IOException {

        File file = new File(directory + "\\" + supportedLanguages.getValue());
        String[] numberOfFiles = file.list();

        int fileNumber = 0;
        if (numberOfFiles != null) {
            fileNumber = numberOfFiles.length + 1;
        } else {
            fileNumber = 1;
        }
        System.out.println("NUMBER OF FILES NUMBER OF FILES NUMBER OF FILES NUMBER OF FILES NUMBER OF FILES NUMBER OF FILES\n" + numberOfFiles.length);
        String fileName = directory + "\\" + supportedLanguages.getValue().toString() + "\\" + supportedLanguages.getValue().toString().toLowerCase() + fileNumber + ".txt";
        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        writeToFile(fileName, textArea, trainOrTest);
    }

    public void writeToFile(String fileDir, TextArea textArea, String trainOrTest){
        try {
            FileWriter myWriter = new FileWriter(fileDir);
            myWriter.write(textArea.getText());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            textInfo.setText("Success! New " + trainOrTest + " file created.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            textInfo.setText("Sorry. Could not create " + trainOrTest + " file.");
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] languages = getLanguages("C:\\Programowanie\\AI_projects\\NeuralNet_LanguageGuesser\\src\\Train");
        for (String language : languages) {
            System.out.println(language);
            supportedLanguages.getItems().add(language);
        }
    }
    public String[] getLanguages(String dir) {
        File file = new File(dir);
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        System.out.println(Arrays.toString(directories));
        return directories;
    }
}
