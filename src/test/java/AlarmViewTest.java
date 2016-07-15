import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.junit.Assert;
import org.junit.Test;
import view.AlarmTemplateBuilder;

/**
 * Created by avpolyakov on 15.07.2016.
 */
public class AlarmViewTest{
    AlarmTemplateBuilder templateBuilder = null;
    VBox alarmNode = null;
    Label fioLabel = null;
    Label postLabel = null;

    @Test
    public void testAlarmVbox(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new JFXPanel(); // Initializes the JavaFx Platform
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        templateBuilder = new AlarmTemplateBuilder();
                        alarmNode = (VBox) templateBuilder.createAlarmNode("Иванов Иван Иванович", "программист", 1);
                        ObservableList<Node> children = alarmNode.getChildren();
                        AnchorPane labels = (AnchorPane) children.get(0);
                        children = labels.getChildren();
                        fioLabel = ((Label)children.get(0));
                        postLabel = ((Label)children.get(1));
                    }
                });
            }
        });
        thread.start();// Initialize the thread
        try {
            Thread.sleep(10000); // Time to use the app, with out this, the thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(fioLabel.getText(), "Иванов Иван Иванович");
        Assert.assertEquals(postLabel.getText(), "программист");
    }
}
