package controller;

import config.HelperFactory;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.CategoryRow;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import static config.HelperFactory.getHelper;

public class AddInterviewController {
    private int interviewId;
    private int candidateId;
    private int interviwerId;

    ObservableList<CategoryRow> marks = FXCollections.observableArrayList();

    @FXML
    TableView<CategoryRow> categoriesTable;
    // Задел на будущее
    @FXML
    TableColumn<CategoryRow, Double> valueCol;
    @FXML
    TableColumn<CategoryRow, Integer> idCol;
    @FXML
    TableColumn<CategoryRow, Category> categoryCol;

    @FXML
    private TextField fioEdit;
    @FXML
    private TextField postEdit;
    @FXML
    private TextField resultEdit;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField interviewerEdit;

    ObservableList<Interviewer> interviewers = FXCollections.observableArrayList();;
    ObservableList<Candidate> candidates = FXCollections.observableArrayList();

    public void addInterview(){
        interviewId = -1;
    }

    public void editInterview(int id) throws SQLException {
        interviewId = id;
        Interview interview = HelperFactory.getHelper().getInterviewById(id);
        fioEdit.setText(interview.getIdCandidate().getFio());
        postEdit.setText(interview.getPost());
        resultEdit.setText(interview.getResult());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(interview.getDate(), formatter);
        datePicker.setValue(date);
        interviewerEdit.setText(interview.getIdInterviewer().getFio());
        marks.addAll(HelperFactory.getHelper().getInterviewMarksAll(id));



    }

    private void saveInterview() throws SQLException {
        DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT);
        if(interviewId == -1){
            HelperFactory.getHelper().addInterview(fioEdit.getText(), interviewerEdit.getText(), df.format(datePicker.getValue()), resultEdit.getText(), postEdit.getText());
        }
    }
}
