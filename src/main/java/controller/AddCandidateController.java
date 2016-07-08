package controller;

import config.HelperFactory;
import entity.Candidate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

/**
 * Created by momelnikov on 08.07.2016.
 */
public class AddCandidateController {
    private Stage dlgStage;
    private int  candidateId;

    @FXML
    private TextField fioEdit;
    @FXML
    private DatePicker dateEdit;
    @FXML
    private TextField banEdit;

    @FXML
    private Button okBtn;
    @FXML
    private Button cancelBtn;
    public void init(Stage stage) throws SQLException {
        dlgStage = stage;
    }
    @FXML
    private void onCancel() throws  SQLException {
        dlgStage.close();
    }

    @FXML
    private void onOkBtn() throws  SQLException {
        addCandidate();
        dlgStage.close();
    }

    public void addCandidate()throws SQLException{
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            HelperFactory.getHelper().addCandidate(fioEdit.getText(),df.format(dateEdit.getValue()),banEdit.getText());
        }
        catch (Exception e) {};

    }
}
