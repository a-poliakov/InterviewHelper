package controller;

import config.HelperFactory;
import entity.Candidate;
import entity.Category;
import entity.Interview;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;



public class EditCategoriesController {
    private Stage primaryStage;

    private Stage dlgStage;

    @FXML
    TextField addEdit;

    @FXML
    Button addBtn;

    @FXML
    Button delBtn;

    private ObservableList<Category> categories = FXCollections.observableArrayList();

    @FXML
    TableView<Category> categoryTable;

    @FXML
    TableColumn<Category, String> nameColumn;

    public void init(Stage stage) throws SQLException {
        dlgStage = stage;
    }

    @FXML
    private void initialize() throws SQLException {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));

        categories.addAll(HelperFactory.getHelper().getCategories());
        categoryTable.setItems(categories);
    }



    private void viewCategoryToTable() throws  SQLException{
        categoryTable.getItems().removeAll();
        categories.clear();
        categories.addAll(HelperFactory.getHelper().getCategories());
        categoryTable.setItems(categories);
    }

    @FXML
    private void onMouseClickAddButton() throws IOException, SQLException{
        Category category = HelperFactory.getHelper().getCategoryByName(addEdit.getText());
        if (category == null) {
            HelperFactory.getHelper().addCategory(addEdit.getText());
            viewCategoryToTable();
        }
    }
    @FXML
    private void onMouseClickDelButton() throws IOException, SQLException{
        int selectedCategoryId = categoryTable.getSelectionModel().getSelectedItem().getIdCategory();
        HelperFactory.getHelper().delCategoryById(selectedCategoryId);
        viewCategoryToTable();
    }

}


