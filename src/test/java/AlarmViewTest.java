import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.junit.Assert;
import org.junit.Test;
import view.AlarmTemplateBuilder;

public class AlarmViewTest{
    @Test
    public void testAlarmVbox(){
        AlarmTemplateBuilder templateBuilder = new AlarmTemplateBuilder();
        VBox alarmNode = (VBox) templateBuilder.createAlarmNode("Иванов Иван Иванович", "программист", 1);
        ObservableList<Node> children = alarmNode.getChildren();
        Label fioLabel = ((Label)children.get(0));
        Assert.assertEquals(fioLabel.getText(), "Иванов Иван Иванович");
        Label postLabel = ((Label)children.get(1));
        Assert.assertEquals(postLabel.getText(), "программист");
    }
}
