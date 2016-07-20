package view;

import config.AppConfig;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by avpolyakov on 15.07.2016.
 */
public class SystemTrayHandler<T> implements EventHandler {
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(Event event) {
        final TrayIcon trayIcon;
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            java.awt.Image image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(AppConfig.TRAY_ICON));
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
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    primaryStage.close();
                }
            });
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
}
