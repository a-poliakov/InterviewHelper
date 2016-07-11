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
import java.time.LocalDate;
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

    public String retName (){
        return fioEdit.getText();
    }



    public void addCandidate()throws SQLException{
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            HelperFactory.getHelper().addCandidate(fioEdit.getText(),df.format(dateEdit.getValue()),banEdit.getText());
        }
        catch (Exception e) {};
    }

    public void editCandidate(String fioCandidate) throws SQLException{
        Candidate candidate = HelperFactory.getHelper().getCandidateByFio(fioCandidate);
        try {
            fioEdit.setText(candidate.getFio());
        } catch (Exception e) {}
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(candidate.getBornDate(), formatter);
            dateEdit.setValue(date);
        } catch (Exception e) {}
        try {
            banEdit.setText(candidate.getBanned());
        } catch (Exception e) {}
    }
}
