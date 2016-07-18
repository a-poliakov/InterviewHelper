package util;

import javafx.application.Platform;
import model.Alarm;
import view.AlarmTemplateBuilder;

import java.util.TimerTask;

public class AlarmTask extends TimerTask {
    private Alarm alarm;
    private AlarmManager context;
    public AlarmTask(Alarm alarm, AlarmManager context) {
        this.alarm=alarm;
    }

    @Override
    public void run() {
        AlarmTemplateBuilder alarmView = new AlarmTemplateBuilder(this);
        // криво, можно лучше
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                alarmView.createNotification(alarm.getFio(), alarm.getPost(), 0);
            }
        });
    }

    public void delayTask(int delayHours, int delayMinutes){
        context.addAlarmTask(alarm, delayHours, delayMinutes);
    }
}
