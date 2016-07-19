package view;

import config.AppConfig;
import config.SystemConfig;
import controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.AlarmManager;
import util.ConstantManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class UIEntry  extends Application{
    private Stage primaryStage;
    private MainController mainController;
    private FXMLLoader fxmlLoader;
    private VBox currentRoot;

    /**
     * Стартует javafx приложение
     * @param primaryStage главная сцена
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);
        this.primaryStage = primaryStage;
        SystemTrayHandler<WindowEvent> systemTrayHandler = new SystemTrayHandler<>();
        systemTrayHandler.setPrimaryStage(primaryStage);
        this.primaryStage.setOnCloseRequest(systemTrayHandler);
        createGUI();
        AlarmManager alarmManager = new AlarmManager(primaryStage);
        alarmManager.updateTodayAlarmList();
        alarmManager.start();
    }

    public static void main(String[] args) throws IOException, SQLException {
        //Если приложение уже запущено, то что-то делаем.
        if(SystemConfig.isRun()) {
            return;
        }
        else {
            //Делаем пометку, что приложение запущено.
            SystemConfig.run();
        }
        if(!SystemConfig.hasAutoRunRegistryKey()){
            SystemConfig.setupAutoRun();
        }
        launch(args);
    }

    /**
     * загружает дерево компонентов главной view из .fxml файла
     * @return VBox возвращает дерево компонентов в виде VBox (корневой элемент в FXML)
     * @throws Exception
     */
    private VBox loadFXML() throws Exception {
        fxmlLoader = new FXMLLoader();
        URL url = getClass().getClassLoader().getResource(AppConfig.FXML_MAIN_URL);
        fxmlLoader.setLocation(url);
        VBox node = null;

        try {
            node = (VBox) fxmlLoader.load();
            System.out.println("fxmlResource = " + AppConfig.FXML_MAIN_URL);
            mainController = fxmlLoader.getController();
            primaryStage.setTitle(ConstantManager.MAIN_VIEW_TITLE);
        } catch (Exception e) {
            System.out.println("Exception on FXMLLoader.load()");
            System.out.println("  * url: " + url);
            System.out.println("  * " + e);
            System.out.println("----------------------------------------\n");
            throw e;
        }
        return node;
    }

    /**
     * создания узла, содержащего главную view
     * @throws Exception
     */
    private void createGUI() throws Exception {
        currentRoot = loadFXML();
        Scene scene = new Scene(currentRoot, 590, 390);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(390);
        primaryStage.setMinWidth(590);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(AppConfig.MAIN_VIEW_ICON));
        primaryStage.show();
    }
}
