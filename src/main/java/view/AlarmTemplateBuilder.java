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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import util.AlarmTask;
import util.ConstantManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlarmTemplateBuilder {
    AlarmTask context;

    public AlarmTemplateBuilder() {
    }

    public AlarmTemplateBuilder(AlarmTask context) {
        this.context = context;
    }

    /**
     * Создается и показывается уведомление
     * @param fioTitle фамилия собеседуемого
     * @param post должность
     * @param alarmId ?? подумать
     */
    public void createNotification(String fioTitle, String post, int alarmId){
        Node graphic = null;
        graphic = createAlarmNode(fioTitle, post, alarmId);
        Notifications notificationBuilder = Notifications.create()
                .title("Предстоящее собеседование")
                .text("")
                .graphic(graphic)
                .hideAfter(Duration.hours(1))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.show();
    }

    /* Создается и заполняется вьюха для напоминаний
     * @param fioTitle фамилия собеседуемого
     * @param post должность
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
        Button holdOverByTime = ((Button) children.get(0));
        ChoiceBox hours = ((ChoiceBox) children.get(1));
        hours.setItems(fillHours());
        hours.setValue(ConstantManager.DEFAULT_DELAY_HOURS);
        ChoiceBox minutes = ((ChoiceBox) children.get(3));
        minutes.setItems(fillMinutes());
        minutes.setValue(ConstantManager.DEFAULT_DELAY_MINUTES);
        holdOverByTime.setOnMouseClicked(event -> {
            context.delayTask((Integer)hours.getValue(), (Integer)minutes.getValue());

        });
        return rootNode;
    }

    /* Заполняется список часов
     * @return ObservableList<Integer> часы
     */
    private ObservableList<Integer> fillHours(){
        ObservableList<Integer> hoursList = FXCollections.observableArrayList();
        List<Integer> hours = new ArrayList<>();
        for(int i = 0; i < 24; i++){
            hours.add(i);
        }
        hoursList.addAll(hours);
        return hoursList;
    }

    /* Заполняется список минут
     * @return ObservableList<Integer> минуты
     */
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
