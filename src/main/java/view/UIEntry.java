package view;

import config.AppConfig;
import controller.AddInterviewController;
import controller.MainController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.ConstantManager;

<<<<<<< HEAD
import javax.imageio.ImageIO;
=======

>>>>>>> 339bfbdc0dedf9135c56f84354d5989122a5ca92
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

// TODO: 05.07.2016 Потренироваться с локализацией
public class UIEntry  extends Application {

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
            System.out.println("    ----------------------------------------\n");
            throw e;
        }
        return node;
    }

    private void createGUI() throws Exception {
        currentRoot = loadFXML();
        Scene scene = new Scene(currentRoot, 590, 390);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(390);
        primaryStage.setMinWidth(590);
        primaryStage.setResizable(false);
<<<<<<< HEAD
        primaryStage.getIcons().add(new Image("icon/mainIcon.png"));
=======

        primaryStage.getIcons().add(new Image("icon/mainIcon.png"));

>>>>>>> 339bfbdc0dedf9135c56f84354d5989122a5ca92
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                final TrayIcon trayIcon;
                if(SystemTray.isSupported()){
                    SystemTray tray = SystemTray.getSystemTray();
                    java.awt.Image image = Toolkit.getDefaultToolkit().getImage("file:src/main/resources/icon/mainIcon.png");



                    ActionListener exitListener = new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Exiting...");
                            System.exit(0);
                        }
                    };

                    final JPopupMenu popup = new JPopupMenu();
                    JMenuItem defaultItem = new JMenuItem("Exit");
                    defaultItem.addActionListener(exitListener);
                    popup.add(defaultItem);

                    trayIcon = new TrayIcon(image, "Tray Demo");

                    ActionListener actionListener = new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            trayIcon.displayMessage("Action Event",
                                    "An Action Event Has Been Performed!",
                                    TrayIcon.MessageType.INFO);
                        }
                    };

                    trayIcon.setImageAutoSize(true);
                    trayIcon.addActionListener(actionListener);
                    trayIcon.addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            if (e.isPopupTrigger()) {
                                popup.setLocation(e.getX(), e.getY());
                                popup.setInvoker(popup);
                                popup.setVisible(true);
                            }
                        }
                    });

                    try {
                        tray.add(trayIcon);
                    } catch (AWTException e) {
                        System.err.println("TrayIcon could not be added.");
                    }
                } else {
                    primaryStage.close();
                }

            }
        });
        primaryStage.getIcons().add(new Image("file:src/main/resources/icon/mainIcon.png"));
<<<<<<< HEAD
=======

>>>>>>> 339bfbdc0dedf9135c56f84354d5989122a5ca92
        primaryStage.show();
    }
}
