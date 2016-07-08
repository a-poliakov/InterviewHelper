package view;

import controller.AddInterviewController;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.ConstantManager;
import java.net.URL;

// TODO: 05.07.2016 Потренироваться с локализацией
public class UIEntry  extends Application {
    private static final String FXML_MAIN = "views/main_view.fxml";

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
    private VBox loadFXML() throws Exception {
        fxmlLoader = new FXMLLoader();
        URL url = getClass().getClassLoader().getResource(FXML_MAIN);
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
            throw e;
        }
        return node;
    }

    private void createGUI() throws Exception {
        currentRoot = loadFXML();
        Scene scene = new Scene(currentRoot, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:src/main/resources/icon/mainIcon.png"));
        primaryStage.show();
    }
}
