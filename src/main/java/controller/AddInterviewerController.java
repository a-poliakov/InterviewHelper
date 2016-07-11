package controller;


import config.HelperFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddInterviewerController {

    Stage primaryStage;

    @FXML
    private TextField fio_txt;

    @FXML
    private void onClickOk() throws SQLException{
        String fio = fio_txt.getText();
        HelperFactory.getHelper().addInterviewer(fio);
        primaryStage.close();
        return;
    }

    @FXML
    private void onClickCansel(){
        primaryStage.close();
    }

    public void init(Stage stage){
        primaryStage = stage;
    }

}
