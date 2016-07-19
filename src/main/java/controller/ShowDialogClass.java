package controller;

import config.AppConfig;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by momelnikov on 14.07.2016.
 */
public class ShowDialogClass<T extends ControllerTemplate> {

    public <T extends ControllerTemplate> T showDialogWindow(Stage primaryStage, String fxmlurl,  int width, int height, String title) throws IOException, SQLException {
        Stage dlgStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getClassLoader().getResource(fxmlurl);
        fxmlLoader.setLocation(url);
        VBox node = (VBox) fxmlLoader.load();
        T controller = fxmlLoader.getController();
        VBox addDlg = node;
        Scene scene = new Scene(addDlg, width, height);
        dlgStage = new Stage();
        dlgStage.setScene(scene);
        dlgStage.setMinHeight(height);
        dlgStage.setMinWidth(width);
        dlgStage.initModality(Modality.APPLICATION_MODAL);
        dlgStage.initOwner(primaryStage);
        dlgStage.setResizable(false);
        dlgStage.setTitle(title);
        dlgStage.getIcons().add(new Image(getClass().getClassLoader().getResource(AppConfig.MAIN_VIEW_ICON).toString()));
        controller.init(dlgStage);
      //  dlgStage.showAndWait();
        return controller;
    }
}