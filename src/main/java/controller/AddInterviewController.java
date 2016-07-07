package controller;

import config.HelperFactory;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TextField expEdit;
    @FXML
    private TextField recommendationEdit;
    @FXML
    private TextField lastWorkEdit;
    @FXML
    private TextArea commentsEdit;

    ObservableList<Interviewer> interviewers = FXCollections.observableArrayList();;
    ObservableList<Candidate> candidates = FXCollections.observableArrayList();


    public void init() throws SQLException {
    }

    public void addInterview() throws SQLException {
        interviewId = 0;
        // устанавливаем тип и значение которое должно хранится в колонке
        valueCol.setCellValueFactory(new PropertyValueFactory<CategoryRow, Double>("value"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<CategoryRow, Category>("category"));
        // заполняем таблицу данными
        marks.addAll(HelperFactory.getHelper().getInterviewMarksAll(interviewId));
        categoriesTable.setItems(marks);
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

        // устанавливаем тип и значение которое должно хранится в колонке
        valueCol.setCellValueFactory(new PropertyValueFactory<CategoryRow, Double>("value"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<CategoryRow, Category>("category"));
        // заполняем таблицу данными
        marks.addAll(HelperFactory.getHelper().getInterviewMarksAll(interviewId));
        categoriesTable.setItems(marks);

        InterviewComment interviewComment = HelperFactory.getHelper().getInterviewCommentByIdInterview(interviewId);
        expEdit.setText(interviewComment.getExperience());
        recommendationEdit.setText(interviewComment.getRecommendations());
        lastWorkEdit.setText(interviewComment.getLastWork());
        commentsEdit.setText(interviewComment.getComment());
    }

    private void saveInterview() throws SQLException {
        DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT);
        if(interviewId == 0){
            Interview interview = HelperFactory.getHelper().addInterview(fioEdit.getText(), interviewerEdit.getText(), df.format(datePicker.getValue()), resultEdit.getText(), postEdit.getText());
            HelperFactory.getHelper().addInterviewComment(interview.getIdInterview(), expEdit.getText(), recommendationEdit.getText(), lastWorkEdit.getText(), commentsEdit.getText());
            HelperFactory.getHelper().addInterviewCategories(interview.getIdInterview(), marks);
        } else{

//            HelperFactory.getHelper().editInterviewDate();
//            HelperFactory.getHelper().editCategory();
//            HelperFactory.getHelper().editInterviewInterviewer();
//            HelperFactory.getHelper().editInterviewPost();
//            HelperFactory.getHelper().editInterviewResult();
              HelperFactory.getHelper().editInterviewCategories(interview.getIdInterview(), marks);
        }
    }
}
