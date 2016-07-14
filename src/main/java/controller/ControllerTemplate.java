package controller;

import javafx.stage.Stage;
import java.sql.SQLException;
/**
 * Created by momelnikov on 14.07.2016.
 */
abstract public class ControllerTemplate {


   abstract public void init(Stage stage) throws SQLException  ;
}
