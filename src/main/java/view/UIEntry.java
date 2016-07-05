package view;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.ConstantManager;
import java.net.URL;

// TODO: 05.07.2016 Потренироваться с локализацией
public class UIEntry  extends Application {
    private static final String FXML_MAIN = "views/addInterviewDlg.fxml";

    private Stage primaryStage;
    private MainController mainController; // пока не нужен
    private FXMLLoader fxmlLoader;
    private VBox currentRoot;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        createGUI();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // загружает дерево компонентов и возвращает в виде VBox (корневой элемент в FXML)
    private VBox loadFXML() {
        fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(FXML_MAIN);
        fxmlLoader.setLocation(url);
        VBox node = null;

        try {
            node = (VBox) fxmlLoader.load();
            System.out.println("fxmlResource = " + FXML_MAIN);
            mainController = fxmlLoader.getController();
            primaryStage.setTitle(ConstantManager.MAIN_VIEW_TITLE);
        } catch (Exception e) {
            System.out.println("Exception on FXMLLoader.load()");
            System.out.println("  * url: " + url);
            System.out.println("  * " + e);
            System.out.println("    ----------------------------------------\n");
        }
        return node;
    }

    private void createGUI() {
        currentRoot = loadFXML();
        Scene scene = new Scene(currentRoot, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(400);
        primaryStage.show();
    }
}
