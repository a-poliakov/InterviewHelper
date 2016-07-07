package controller;

import config.HelperFactory;
import entity.Candidate;
import entity.Interview;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.UIEntry;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.sun.deploy.util.UpdateCheck.showDialog;

public class MainController {

    private static final String FXML_ADD_INTERVIEW_DLG = "views/add_interview_dlg.fxml";

    private ObservableList<Interview> interviews = FXCollections.observableArrayList();

    private VBox addInterviewDlg;

    private Stage primaryStage;

    private Stage dlgStage;

    private AddInterviewController addInterviewController;

    @FXML
    TableView<Interview> mainTable;

    @FXML
    TableColumn<Interview, String> fioColumn;

    @FXML
    TableColumn<Interview, String> postColumn;

    @FXML
    TableColumn<Interview, String> dateColumn;

    // инициализируем форму данными
    @FXML
    private void initialize() throws SQLException {

        mainTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    try {

                        onMouseClicked();
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
    private void onMouseClicked() throws IOException, SQLException {
        //System.out.print("hello!");
        int selectedInterviewId = mainTable.getSelectionModel().getSelectedItem().getIdInterview();
        showInterviewDlg();
        addInterviewController.editInterview(selectedInterviewId);
    }

    @FXML
    private void onNewInterviewAction() throws IOException, SQLException {
        showInterviewDlg();
        addInterviewController.addInterview();
    }

    private void showInterviewDlg() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getClassLoader().getResource(FXML_ADD_INTERVIEW_DLG);
        fxmlLoader.setLocation(url);
        VBox node = null;
        node = (VBox) fxmlLoader.load();
        addInterviewController = fxmlLoader.getController();
        addInterviewDlg = node;
        Scene scene = new Scene(addInterviewDlg, 630, 500);
        dlgStage = new Stage();
        dlgStage.setScene(scene);
        dlgStage.setMinHeight(500);
        dlgStage.setMinWidth(630);
        dlgStage.initModality(Modality.WINDOW_MODAL);
        dlgStage.initOwner(primaryStage);
        dlgStage.show();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
