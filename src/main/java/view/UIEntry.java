package view;

import config.AppConfig;
import controller.AddInterviewController;
import controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.ConstantManager;

import javax.imageio.ImageIO;
import javax.imageio.ImageIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.management.ThreadInfo;
import java.net.URL;

// TODO: 05.07.2016 Потренироваться с локализацией
public class UIEntry  extends Application{

    private Stage primaryStage;
    private MainController mainController;
    private FXMLLoader fxmlLoader;
    private VBox currentRoot;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);
        this.primaryStage = primaryStage;
        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                final TrayIcon trayIcon;
                if (SystemTray.isSupported()) {
                    SystemTray tray = SystemTray.getSystemTray();
                    java.awt.Image image = Toolkit.getDefaultToolkit().getImage("src/main/resources/icon/mainIcon.png");
                    final JPopupMenu popup = new JPopupMenu();
                    trayIcon = new TrayIcon(image, "Interview Helper");
                    ActionListener restoreListener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            restoreWindow();
                            tray.remove(trayIcon);
                        }
                    };
                    ActionListener exitListener = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Exiting...");
                            System.exit(0);
                        }
                    };
                    JMenuItem restoreItem = new JMenuItem("Restore window");
                    restoreItem.addActionListener(restoreListener);
                    popup.add(restoreItem);
                    JMenuItem exitItem = new JMenuItem("Exit");
                    exitItem.addActionListener(exitListener);
                    popup.add(exitItem);
                    trayIcon.setImageAutoSize(true);
                    trayIcon.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            restoreWindow();
                            tray.remove(trayIcon);
                        }

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
                        trayIcon.displayMessage("Interview Helper",
                                "Приложение продолжит работу в трее",
                                TrayIcon.MessageType.INFO);
                    } catch (AWTException e) {
                        System.err.println("TrayIcon could not be added.");
                    }
                } else {
                    primaryStage.close();
                }
            }

            public void restoreWindow(){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        primaryStage.show();
                    }
                });
            }
        });
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
            System.out.println("----------------------------------------\n");
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
        primaryStage.getIcons().add(new Image("icon/mainIcon.png"));
        primaryStage.show();
        AlarmTemplateBuilder templateBuilder = new AlarmTemplateBuilder();
        templateBuilder.createNotification("Иванов Иван Иванович", "программист", "19.06.16", 1);
    }
}
