package controller;

import entity.Category;
import entity.Mark;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.CategoryRow;

import java.net.URL;
import java.util.ResourceBundle;

public class AddInterviewController {
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
    public void initialize() {

    }

    public void addInterview(){

    }

    public void editInterview(){

    }
}
