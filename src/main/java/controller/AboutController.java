package controller;

import config.AppConfig;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;

public class AboutController extends ControllerTemplate {

    Stage primaryStage ;

    @Override
    public void init(Stage stage) throws SQLException {
        primaryStage = stage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @FXML
    public void openReadmeFile() {
        String testFilePath = "readme.md";//"c:\\temp\\test.txt";
        try {
            // Microsoft Windows NT or later
            Process process = Runtime.getRuntime().exec("cmd /c notepad.exe " + testFilePath);

            // Microsoft Windows 95/98
            // Runtime.getRuntime().exec("c:\\windows\\notepad.exe " + testFilePath);
            process.waitFor();
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }

    }
}
