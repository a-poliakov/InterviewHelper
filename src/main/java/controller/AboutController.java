package controller;

import javafx.stage.Stage;

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
}
