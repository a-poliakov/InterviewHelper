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

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddInterviewController extends ControllerTemplate {
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
        // загружаем из Dao комментарий к интервью
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(interview.getIdCandidate().getBornDate(), formatter);
            birthDatePicker.setValue(date);
        } catch (Exception e){}
        try {
            postEdit.setText(interview.getPost());
        } catch (Exception e){}
        try {
            resultEdit.setText(interview.getResult());
        } catch (Exception e){}
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(interview.getDate(), formatter);
            datePicker.setValue(date);
        } catch (Exception e){}
        try {
            interviewerEdit.setText(interview.getIdInterviewer().getFio());
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
                    return Double.parseDouble(string);
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
    private void saveInterview() throws SQLException {
        Interview interview = HelperFactory.getHelper().editOrAddInterview(
                interviewId, DateUtil.format(datePicker.getValue()),
                candidateId, fioEdit.getText(), DateUtil.format(birthDatePicker.getValue()),
                interviewerId, interviewerEdit.getText(),
                resultEdit.getText(), postEdit.getText(), marks);
        HelperFactory.getHelper().addOrEditInterviewComment(interview.getIdInterview(), expEdit.getText(), recommendationEdit.getText(), lastWorkEdit.getText(), commentsEdit.getText());
        dlgAddInterviewStage.close();
    }
}
