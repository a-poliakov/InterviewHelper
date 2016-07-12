package controller;

import config.AppConfig;
import config.HelperFactory;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.CategoryRow;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import util.ConstantManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddInterviewController {
    // ID используемых данных
    private int interviewId;
    private int candidateId;
    private int interviwerId;

    // Таблица оценок
    @FXML
    TableView<CategoryRow> categoriesTable;
    @FXML
    TableColumn<CategoryRow, Double> valueCol;
    @FXML
    TableColumn<CategoryRow, Category> categoryCol;


    private VBox addInterviewerDlg;
    private VBox addCandidateDlg;
    private AddCandidateController addCandidateController;

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
    @FXML
    private Button okBtn;
    @FXML
    private Button addCandidateBtn;
    @FXML
    private Button addInterviewerBtn;

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

    static Candidate candidate;
    static Interviewer interviewer;

    public void init(Stage stage) throws SQLException {
        dlgAddInterviewStage = stage;
        possibleCandidateSuggestions.addAll(HelperFactory.getHelper().getCandidates());
        autoCompletionCandidateBinding = TextFields.bindAutoCompletion(
                fioEdit, possibleCandidateSuggestions);
        possibleInterviewerSuggestions.addAll(HelperFactory.getHelper().getInterviewers());
        autoCompletionInterviewerBinding = TextFields.bindAutoCompletion(
                interviewerEdit, possibleInterviewerSuggestions);
        autoCompletionCandidateBinding.setOnAutoCompleted(event -> {
            candidate = event.getCompletion();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(candidate.getBornDate(), formatter);
            datePicker.setValue(date);
            candidateId = candidate.getIdCandidate();
        });
        autoCompletionInterviewerBinding.setOnAutoCompleted(event -> {
            interviewer = event.getCompletion();
            // TODO: 12.07.2016 Что за херня? 
            //interviewId = interviewer.getIdInterviewer();
        });
    }



    @FXML
    private void onDialogResult() throws IOException, SQLException {
        saveInterview();
    }

    @FXML
    private void onAddCandidateAction() throws IOException, SQLException {
        ShowAddCandidateDialog();
    }


    public void ShowAddCandidateDialog() throws  IOException,SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getClassLoader().getResource(AppConfig.FXML_ADD_CANDIDATE_DLG_URL);
        fxmlLoader.setLocation(url);
        VBox node = null;
        node = (VBox) fxmlLoader.load();
        addCandidateController = fxmlLoader.getController();
        addCandidateDlg = node;
        Scene scene = new Scene(addCandidateDlg, 419.0, 180);
        dlgCandidateStage = new Stage();
        dlgCandidateStage.setScene(scene);
        dlgCandidateStage.setMinHeight(180);
        dlgCandidateStage.setMinWidth(419.0);
        dlgCandidateStage.initModality(Modality.WINDOW_MODAL);
        dlgCandidateStage.initOwner(dlgAddInterviewStage);
        addCandidateController.init(dlgCandidateStage);
        addCandidateController.editCandidate(fioEdit.getText());
        dlgCandidateStage.showAndWait();
        fioEdit.setText(addCandidateController.retName());
    }

    public void addInterview() throws SQLException {
        interviewId = 0;
        try {
            // устанавливаем тип и значение которое должно хранится в колонке
            valueCol.setCellValueFactory(new PropertyValueFactory<CategoryRow, Double>("value"));
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
                    TextFieldTableCell.<CategoryRow,Double>forTableColumn(converter));
            valueCol.setOnEditCommit(
                    (TableColumn.CellEditEvent<CategoryRow, Double> t) -> {
                        ((CategoryRow) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setValue(t.getNewValue());
                    });
        }catch (Exception e){

        }
    }

    public void editInterview(int id) throws SQLException {
        interviewId = id;
        Interview interview = HelperFactory.getHelper().getInterviewById(id);
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
        } catch (Exception e){}
        try {
            InterviewComment interviewComment = HelperFactory.getHelper().getInterviewCommentByIdInterview(interviewId);
            // TODO: 07.07.2016 Нарушена целостность базы данных. Поэтому если у интервью нет interviewComment то создадим пустой
            if (interviewComment == null) {
                interviewComment = HelperFactory.getHelper().addInterviewComment(interviewId, "", "", "", "");
            }
            expEdit.setText(interviewComment.getExperience());
            recommendationEdit.setText(interviewComment.getRecommendations());
            lastWorkEdit.setText(interviewComment.getLastWork());
            commentsEdit.setText(interviewComment.getComment());
        } catch (Exception e){}
    }

    private void saveInterview() throws SQLException {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if(interviewId == 0){
            Interview interview = HelperFactory.getHelper().addInterview(fioEdit.getText(), df.format(birthDatePicker.getValue()), interviewerEdit.getText(), df.format(datePicker.getValue()), resultEdit.getText(), postEdit.getText());
            HelperFactory.getHelper().addInterviewComment(interview.getIdInterview(), expEdit.getText(), recommendationEdit.getText(), lastWorkEdit.getText(), commentsEdit.getText());
            HelperFactory.getHelper().addInterviewMarks(interview.getIdInterview(), marks);
        } else{
            HelperFactory.getHelper().editInterview(interviewId, df.format(birthDatePicker.getValue()), df.format(datePicker.getValue()), resultEdit.getText(), postEdit.getText(), marks);
            HelperFactory.getHelper().editInterviewComment(interviewId, expEdit.getText(), recommendationEdit.getText(), lastWorkEdit.getText(), commentsEdit.getText());
        }
        dlgAddInterviewStage.close();
    }
}
