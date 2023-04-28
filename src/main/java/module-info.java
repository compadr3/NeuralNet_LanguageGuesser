module com.example.neuralnet_languageguesser {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.neuralnet_languageguesser to javafx.fxml;
    exports com.example.neuralnet_languageguesser;
}