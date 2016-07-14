package controller;

import config.AppConfig;
import config.DatabaseHelper;
import config.HelperFactory;
import entity.Interview;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.ConstantManager;
import controller.ShowDialogClass;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;

import static util.DBUtil.*;

public class MainController {
    // Связывание данных
    private ObservableList<Interview> interviews = FXCollections.observableArrayList(); // источник данных для интервью
    // Сцены
    private VBox addInterviewDlg;
    private VBox addInterviewerDlg;
    private VBox editCategoryDlg;
    private VBox addCandidateDlg;
    private Stage primaryStage;
    private Stage dlgStage; // ?

    @FXML
    TableView<Interview> mainTable;
    @FXML
    TableColumn<Interview, String> fioColumn;
    @FXML
    TableColumn<Interview, String> postColumn;
    @FXML
    TableColumn<Interview, String> dateColumn;
    @FXML
    TextField fioFilter;
    @FXML
    TextField postFilter;
    @FXML
    TextField dateFilter;


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // инициализируем форму данными
    @FXML
    private void initialize() throws SQLException {
        // реакция на нажатие кнопки DELETE
        mainTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.DELETE) {
                    try {
                        onDeleteInterview();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        // реакция на двойной щелчок по таблице
        mainTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    try {
                        onEditInterview();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // устанавливаем тип и значение которое должно хранится в колонке
        fioColumn.setCellValueFactory(new PropertyValueFactory<Interview, String>("idCandidate"));
        postColumn.setCellValueFactory(new PropertyValueFactory<Interview, String>("post"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Interview, String>("date"));
        // заполняем таблицу данными
        interviews.addAll(HelperFactory.getHelper().getInterview());
        mainTable.setItems(interviews);
    }

    @FXML
    private void onNewInterviewAction() throws IOException, SQLException {

        ShowDialogClass<AddInterviewController> showDialogClass = new ShowDialogClass<AddInterviewController>();
        AddInterviewController addInterviewController = showDialogClass.showDialogWindow(primaryStage,AppConfig.FXML_ADD_INTERVIEW_DLG_URL,630,500,ConstantManager.ADD_INTERVIEW_TITLE);
        addInterviewController.addInterview();
        addInterviewController.getDlgAddInterviewStage().showAndWait();

        String fio = fioFilter.getText();
        String post = postFilter.getText();
        String date = dateFilter.getText();

        interviews.clear();
        interviews.addAll(HelperFactory.getHelper().getInterviewsByCandidateFioAndDateAndPost(fio, post, date));
    }

    @FXML
    private void onEditCategoryAction() throws IOException, SQLException {
        ShowDialogClass<EditCategoriesController> showDialogClass = new ShowDialogClass<EditCategoriesController>();
        EditCategoriesController editCategoriesController = showDialogClass.showDialogWindow(primaryStage,AppConfig.FXML_EDIT_CATEGORY_DLG_URL,300,300,ConstantManager.EDIT_CATEGORY_TITLE);
        editCategoriesController.getPrimaryStage().showAndWait();
    }

    @FXML
    private void onAboutAction() throws IOException, SQLException {
        ShowDialogClass<AboutController> showDialogClass = new ShowDialogClass<AboutController>();
        AboutController controller = showDialogClass.showDialogWindow(primaryStage, AppConfig.FXML_ABOUT_DLG_URL,  265,190, ConstantManager.ABOUT_TITLE);
        controller.getPrimaryStage().showAndWait();
    }

    @FXML
    private void onFilter() throws SQLException {
        String fio = fioFilter.getText();
        String post = postFilter.getText();
        String date = dateFilter.getText();

        interviews.clear();
        interviews.addAll(HelperFactory.getHelper().getInterviewsByCandidateFioAndDateAndPost(fio, post, date));
    }

    private void onDeleteInterview() throws SQLException {
        Interview selectedInterview = mainTable.getSelectionModel().getSelectedItem();
        if (selectedInterview != null) {
            int selectedInterviewId = selectedInterview.getIdInterview();
            HelperFactory.getHelper().delInterviewById(selectedInterviewId);
            interviews.remove(selectedInterview);
        }
    }

    private void onEditInterview() throws IOException, SQLException {
        // получаем информацию о выделенном собеседовании
        Interview selectedInterview = mainTable.getSelectionModel().getSelectedItem();
        int selectedInterviewId = selectedInterview.getIdInterview();
        int interviewPosition = mainTable.getSelectionModel().getSelectedIndex();
        // вызываем диалог редактирования
        ShowDialogClass<AddInterviewController> showDialogClass = new ShowDialogClass<AddInterviewController>();
        AddInterviewController addInterviewController = showDialogClass.showDialogWindow(primaryStage,AppConfig.FXML_ADD_INTERVIEW_DLG_URL,630,500,ConstantManager.ADD_INTERVIEW_TITLE);
        addInterviewController.editInterview(selectedInterviewId);
        addInterviewController.getDlgAddInterviewStage().showAndWait();
        // получаем из БД обновленное собеседование, удаляем старое из таблицы и вставляем новое
        Interview refreshedInterview = HelperFactory.getHelper().getInterviewById(selectedInterviewId);
        interviews.remove(selectedInterview);
        interviews.add(interviewPosition, refreshedInterview);
    }




}
