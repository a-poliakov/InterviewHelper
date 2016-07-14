package view;

import config.AppConfig;
import entity.Interview;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlarmTemplateBuilder {
    /* Создается уведомление
     * @param fioTitle фамилия собеседуемого
     * @param alarmDate дата собеседования
     * @param alarmId ?? подумать
     * @return Node созданный Node для напоминания
     */
    public void createNotification(String fioTitle, String post, int alarmId){
        Node graphic = null;
        graphic = createAlarmNode(fioTitle, post, alarmId);
        Notifications notificationBuilder = Notifications.create()
                .title("Предстоящее собеседование")
                .text("")
                .graphic(graphic)
                .hideAfter(Duration.seconds(30))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent arg0) {
                        System.out.println("Notification clicked on!");
                    }
                });
        notificationBuilder.show();
    }

    /* Создается и заполняется вьюха для напоминаний
     * @param fioTitle фамилия собеседуемого
     * @param alarmDate дата собеседования
     * @param alarmId ?? подумать
     * @return Node созданный Node для напоминания
     */
    public Node createAlarmNode(String fioTitle, String post, int alarmId){
        VBox rootNode = null;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(AppConfig.FXML_ALARM_URL));
        try {
            rootNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObservableList<Node> children = rootNode.getChildren();
        AnchorPane labelsPane = ((AnchorPane)children.get(0));
        AnchorPane buttonPane = ((AnchorPane)children.get(1));
        children = labelsPane.getChildren();
        Label fioLabel = ((Label)children.get(0));
        fioLabel.setText(fioTitle);
        Label postLabel = ((Label)children.get(1));
        postLabel.setText(post);
        children = buttonPane.getChildren();
        Button holdOverDefault = ((Button) children.get(0));
        Button holdOverByTime = ((Button) children.get(1));
        ChoiceBox hours = ((ChoiceBox) children.get(2));
        hours.setItems(fillHours());
        ChoiceBox minutes = ((ChoiceBox) children.get(4));
        minutes.setItems(fillMinutes());
        return rootNode;
    }

    private ObservableList<Integer> fillHours(){
        ObservableList<Integer> hoursList = FXCollections.observableArrayList();
        List<Integer> hours = new ArrayList<>();
        for(int i = 0; i < 24; i++){
            hours.add(i);
        }
        hoursList.addAll(hours);
        return hoursList;
    }

    private ObservableList<Integer> fillMinutes(){
        ObservableList<Integer> minutesList = FXCollections.observableArrayList();
        List<Integer> minutes = new ArrayList<>();
        for(int i = 0; i < 59; i++){
            minutes.add(i);
        }
        minutesList.addAll(minutes);
        return minutesList;
    }
}
