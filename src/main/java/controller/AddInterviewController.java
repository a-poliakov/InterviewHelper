package controller;

import config.HelperFactory;
import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.CategoryRow;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddInterviewController {
    private int interviewId;
    private int candidateId;
    private int interviwerId;
    private Stage dlgStage;

    ObservableList<CategoryRow> marks = FXCollections.observableArrayList();
    private static final String FXML_ADD_CANDIDATE_DLG = "views/add_candidate_dlg.fxml";
    private static final String FXML_ADD_INTERVIEWER_DLG = "views/add_interviewer_dlg.fxml";

    @FXML
    TableView<CategoryRow> categoriesTable;
    // Задел на будущее
    @FXML
    TableColumn<CategoryRow, Double> valueCol;
    @FXML
    TableColumn<CategoryRow, Category> categoryCol;


    private VBox addInterviewerDlg;

    private VBox addCandidateDlg;

    private AddCandidateController addCandidateController;

    private Stage primaryStage;

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
    @FXML
    private Button okBtn;
    @FXML
    private Button addCan;
    @FXML
    private Button addInter;

    ObservableList<Interviewer> interviewers = FXCollections.observableArrayList();;
    ObservableList<Candidate> candidates = FXCollections.observableArrayList();
    private Stage dlg1Stage;
    private Stage dlgStageNew;


    public void init(Stage stage) throws SQLException {
        dlgStage = stage;
    }

    @FXML
    private void onDialogResult() throws IOException, SQLException {
        saveInterview();
    }

    @FXML
    private  void addCanClick() throws IOException,SQLException{
        ShowAddCandidateDialog();
    }


    public void ShowAddCandidateDialog() throws  IOException,SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getClassLoader().getResource(FXML_ADD_CANDIDATE_DLG);
        fxmlLoader.setLocation(url);
        VBox node = null;
        node = (VBox) fxmlLoader.load();
        addCandidateController = fxmlLoader.getController();
        addCandidateDlg = node;
        Scene scene = new Scene(addCandidateDlg, 419.0, 180);
        dlgStageNew = new Stage();
        dlgStageNew.setScene(scene);
        dlgStageNew.setMinHeight(180);
        dlgStageNew.setMinWidth(419.0);
        dlgStageNew.initModality(Modality.WINDOW_MODAL);
        dlgStageNew.initOwner(dlgStage);
        addCandidateController.init(dlgStageNew);
        addCandidateController.editCandidate(fioEdit.getText());
        dlgStageNew.showAndWait();
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
            Interview interview = HelperFactory.getHelper().addInterview(fioEdit.getText(), interviewerEdit.getText(), df.format(datePicker.getValue()), resultEdit.getText(), postEdit.getText());
            HelperFactory.getHelper().addInterviewComment(interview.getIdInterview(), expEdit.getText(), recommendationEdit.getText(), lastWorkEdit.getText(), commentsEdit.getText());
            HelperFactory.getHelper().addInterviewMarks(interview.getIdInterview(), marks);
        } else{
            HelperFactory.getHelper().editInterview(interviewId, df.format(datePicker.getValue()), resultEdit.getText(), postEdit.getText(), marks);
            HelperFactory.getHelper().editInterviewComment(interviewId, expEdit.getText(), recommendationEdit.getText(), lastWorkEdit.getText(), commentsEdit.getText());
        }
        dlgStage.close();
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
