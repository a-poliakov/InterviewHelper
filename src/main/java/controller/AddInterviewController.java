package controller;

import config.HelperFactory;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.CategoryRow;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import util.DateUtil;
import util.TimeSpinner;
import util.Validator;
import view.DialogManager;
import view.ExceptionListener;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AddInterviewController extends ControllerTemplate implements ExceptionListener {
    // ID используемых данных
    private int interviewId=0;
    private int candidateId=0;
    private int interviewerId=0;

    // Таблица оценок
    @FXML
    TableView<CategoryRow> categoriesTable;
    @FXML
    TableColumn<CategoryRow, Double> valueCol;
    @FXML
    TableColumn<CategoryRow, Category> categoryCol;


    private VBox addInterviewerDlg;
    private VBox addCandidateDlg;

    @FXML
    private TextField fioEdit;
    @FXML
    private TextField postEdit;
    @FXML
    private TextField resultEdit;
    @FXML
    private DatePicker datePicker;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TimeSpinner timeSpinner;
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

    public Stage getDlgAddInterviewStage() {
        return dlgAddInterviewStage;
    }

    // Связывание данных
    ObservableList<CategoryRow> marks = FXCollections.observableArrayList(); // источник данных для оценок
    ObservableList<Interviewer> interviewers = FXCollections.observableArrayList();; // источник для интервьюверов
    ObservableList<Candidate> candidates = FXCollections.observableArrayList(); // источник для кандидатов

    // Сцены
    private Stage dlgAddInterviewStage; // сцена для добавления интервью
    private Stage dlgAddInterviewerStage; // сцена для добавления интервьювера
    private Stage dlgCandidateStage; // сцена для добавления кандидата

    // binding для "живого" поиска
    private AutoCompletionBinding<Candidate> autoCompletionCandidateBinding;
    private ObservableSet<Candidate> possibleCandidateSuggestions = FXCollections.observableSet();
    private AutoCompletionBinding<Interviewer> autoCompletionInterviewerBinding;
    private ObservableSet<Interviewer> possibleInterviewerSuggestions = FXCollections.observableSet();

    /**
     * Метод для инициализации компонентов формы
     * @param stage окно диалога
     * @throws SQLException
     */
    public void init(Stage stage) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse("01.01.1990", formatter);
        birthDatePicker.setValue(DateUtil.parse("01.01.1990"));
        dlgAddInterviewStage = stage;
        initAutoCompletion();
    }

    /**
     * Инициализация живого поиска
     * @throws SQLException
     */
    private void initAutoCompletion()throws SQLException {
        possibleCandidateSuggestions.addAll(HelperFactory.getHelper().getCandidates());
        autoCompletionCandidateBinding = TextFields.bindAutoCompletion(
                fioEdit, possibleCandidateSuggestions);
        possibleInterviewerSuggestions.addAll(HelperFactory.getHelper().getInterviewers());
        autoCompletionInterviewerBinding = TextFields.bindAutoCompletion(
                interviewerEdit, possibleInterviewerSuggestions);
        autoCompletionCandidateBinding.setOnAutoCompleted(event -> {
            Candidate candidate = event.getCompletion();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(candidate.getBornDate(), formatter);
            birthDatePicker.setValue(date);
            candidateId = candidate.getIdCandidate();
        });
        autoCompletionInterviewerBinding.setOnAutoCompleted(event -> {
            Interviewer interviewer = event.getCompletion();
            interviewerId = interviewer.getIdInterviewer();
        });
    }

    /**
     * Обработка результата диалога (по нажатию на кнопку "ОК")
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    private void onDialogResult() throws IOException, SQLException {
        saveInterview();
    }

    /**
     * Добавление нового интервью
     * @throws SQLException
     */
    public void addInterview() throws SQLException {
        interviewId = 0;
        fillMarksTable();
    }

    /**
     * Редактирование существующего интервью
     * @throws SQLException
     */
    public void editInterview(int id) throws SQLException {
        interviewId = id;
        Interview interview = HelperFactory.getHelper().getInterviewById(id);
        candidateId = interview.getIdCandidate().getIdCandidate();
        interviewerId = interview.getIdInterviewer().getIdInterviewer();
        fillRequiredFields(interview);
        fillMarksTable();
        fillCommentsFilds();
    }

    /**
     * Метод для заполнения обязательных полей интервью
     * @param interview интервью, из которого берутся данные
     */
    private void fillRequiredFields(Interview interview){
        try {
            fioEdit.setText(interview.getIdCandidate().getFio());
        } catch (Exception e){}
        try {
            birthDatePicker.setValue(DateUtil.parse(interview.getIdCandidate().getBornDate()));
        } catch (Exception e){}
        try {
            postEdit.setText(interview.getPost());
        } catch (Exception e){}
        try {
            resultEdit.setText(interview.getResult());
        } catch (Exception e){}
        try {
            datePicker.setValue(DateUtil.parse(interview.getDate()));
        } catch (Exception e){}
        try {
            interviewerEdit.setText(interview.getIdInterviewer().getFio());
        } catch (Exception e){}
        try {
            timeSpinner.getValueFactory().setValue(fromString(interview.getTime()));
        } catch (Exception e){}
    }

    /**
     * Метод для заполнения таблицы оценок интервью
     */
    private void fillMarksTable(){
        try {
            // устанавливаем тип и значение которое должно хранится в колонке
            valueCol.setCellValueFactory(new PropertyValueFactory("value"));
            categoryCol.setCellValueFactory(new PropertyValueFactory<CategoryRow, Category>("category"));
            // заполняем таблицу данными
            marks.addAll(HelperFactory.getHelper().getInterviewMarksAll(interviewId));
            categoriesTable.setItems(marks);
            categoriesTable.setEditable(true);
            StringConverter<Double> converter = new StringConverter<Double>() {
                @Override
                public String toString(Double object) {
                    return object.toString();
                }

                @Override
                public Double fromString(String string) {
                    try{
                        return Double.parseDouble(string);
                    } catch (NumberFormatException e) {
                        try{
                            if(string.contains(",")){
                                string = string.replace(',', '.');
                            } else if(string.contains(".")){
                                string = string.replace('.', ',');
                            }
                            return Double.parseDouble(string);
                        }catch (NumberFormatException ex){
                            handleExceptionAndShowDialog(ex);
                            return  0.0;
                        }
                    }
//                    string = string.replace(',', '.');
                }
            };
            valueCol.setCellFactory(
                    TextFieldTableCell.<CategoryRow, Double>forTableColumn(converter));
            valueCol.setOnEditCommit(
                    (TableColumn.CellEditEvent<CategoryRow, Double> t) -> {
                        ((CategoryRow) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setValue(t.getNewValue());
                    });
        } catch (SQLException e){}
    }

    /**
     * Метод для заполнения комментариев к интервью
     */
    private void fillCommentsFilds(){
        try {
            InterviewComment interviewComment = HelperFactory.getHelper().getInterviewCommentByIdInterview(interviewId);
            if (interviewComment == null) {
                interviewComment = HelperFactory.getHelper().addOrEditInterviewComment(interviewId, "", "", "", "");
            }
            expEdit.setText(interviewComment.getExperience());
            recommendationEdit.setText(interviewComment.getRecommendations());
            lastWorkEdit.setText(interviewComment.getLastWork());
            commentsEdit.setText(interviewComment.getComment());
        } catch (Exception e){}
    }

    /**
     * Сохранение интервью
     * (передает введенные данные об интервью в Dao)
     * @throws SQLException
     */
    private void saveInterview(){
        try {
            timeSpinner.getValueFactory().setValue(fromString(timeSpinner.getEditor().getText()));
            Validator.checkFio(fioEdit.getText());
            Validator.checkFio(interviewerEdit.getText());
            Validator.checkDate(DateUtil.format(datePicker.getValue()));
            Validator.checkDate(DateUtil.format(birthDatePicker.getValue()));
            Interview interview = HelperFactory.getHelper().editOrAddInterview(
                    interviewId, DateUtil.format(datePicker.getValue()),
                    candidateId, fioEdit.getText(), DateUtil.format(birthDatePicker.getValue()),
                    interviewerId, interviewerEdit.getText(),
                    resultEdit.getText(), postEdit.getText(),
                    timeSpinner.getValue().toString(),
                    marks);
            HelperFactory.getHelper().addOrEditInterviewComment(interview.getIdInterview(), expEdit.getText(), recommendationEdit.getText(), lastWorkEdit.getText(), commentsEdit.getText());
            dlgAddInterviewStage.close();
        } catch (Exception e){
            handleExceptionAndShowDialog(e);
        }
    }

    /**
     * Обрабатывает произошедшие в ui исключения и отображает диалоговое окно
     * @param throwable произошедшее исключение
     */
    @Override
    public void handleExceptionAndShowDialog(Throwable throwable) {
        DialogManager.showErrorDialog("It's an error, breathe deeply", throwable.getMessage());
    }

    /**
     * Обрабатывает исключения и отображает в консоль
     * @param exception
     */
    @Override
    public void handleExceptionAndDisplayItInCodeArea(Exception exception) {

    }

    private LocalTime fromString(String string) {
        String[] tokens = string.split(":");
        int hours = getIntField(tokens, 0);
        int minutes = getIntField(tokens, 1) ;
        int seconds = getIntField(tokens, 2);
        int totalSeconds = (hours * 60 + minutes) * 60 + seconds ;
        return LocalTime.of((totalSeconds / 3600) % 24, (totalSeconds / 60) % 60, seconds % 60);
    }

    private int getIntField(String[] tokens, int index) {
        if (tokens.length <= index || tokens[index].isEmpty()) {
            return 0 ;
        }
        return Integer.parseInt(tokens[index]);
    }
}
