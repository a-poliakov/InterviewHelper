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
        primaryStage = stage;
    }

    @FXML
    private void initialize() throws SQLException {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));
        categories.addAll(HelperFactory.getHelper().getCategories());
        categoryTable.setItems(categories);
    }

    @FXML
    private void onMouseClickAddButton() throws IOException, SQLException {
        if (addEdit.getLength() > 0) {
            addCategory();
        }
    }

    @FXML
    private void onMouseClickDelButton() throws IOException, SQLException {
        if (categoryTable.getSelectionModel().getSelectedItem() != null ) {
            removeCategory();
        }
    }

    private void removeCategory()throws IOException, SQLException {
        Category category = categoryTable.getSelectionModel().getSelectedItem();
        int selectedCategoryId = category.getIdCategory();
        HelperFactory.getHelper().delCategoryById(selectedCategoryId);
        categories.remove(category);
    }

    private void addCategory()throws IOException, SQLException {
        String newCategoryName = addEdit.getText();
        Category category = HelperFactory.getHelper().getCategoryByName(newCategoryName);
        if (category == null) {
            category = HelperFactory.getHelper().addCategory(newCategoryName);
            addEdit.clear();
            categories.add(category);
            categoryTable.setItems(categories);
        }
    }
}


