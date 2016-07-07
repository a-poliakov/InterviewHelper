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
    ObservableList<CategoryRow> marks = FXCollections.observableArrayList();

    @FXML
    TableView<CategoryRow> categoriesTable;
    @FXML
    TableColumn<CategoryRow, Double> valueCol;
    @FXML
    TableColumn<CategoryRow, Integer> idCol;
    @FXML
    TableColumn<CategoryRow, Category> categoryCol;

    @FXML
    TextField postEdit;
    @FXML
    TextField resultEdit;
    @FXML
    DatePicker datePicker;

    ObservableList<Interviewer> interviewers = FXCollections.observableArrayList();;
    ObservableList<Candidate> candidates = FXCollections.observableArrayList();

    public void addInterview(){
        interviewId = -1;
    }

    public void editInterview(int id) throws SQLException {
        //заполнение
        //System.out.print(id);

        interviewId = id;
        Interview interview = HelperFactory.getHelper().getInterviewById(id);
        postEdit.setText(interview.getIdCandidate().getFio());
        resultEdit.setText(interview.getResult());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse("12.11.2015", formatter);
        datePicker.setValue(date);
        marks.addAll(HelperFactory.getHelper().getInterviewMarksAll(id));



    }
}
