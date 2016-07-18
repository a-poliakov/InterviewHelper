package util;

import javafx.application.Platform;
import model.Alarm;
import view.AlarmTemplateBuilder;

import javax.sound.sampled.Clip;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.TimerTask;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import static java.lang.Thread.sleep;

public class AlarmTask extends TimerTask {
    private Alarm alarm;
    private AlarmManager context;
    public AlarmTask(Alarm alarm, AlarmManager context) {
        this.alarm = alarm;
        this.context = context;
    }

    @Override
    public void run() {
        AlarmTemplateBuilder alarmView = new AlarmTemplateBuilder(this);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                alarmView.createNotification(alarm.getFio(), alarm.getPost(), 0);
            }
        });
        //Sound.playSound("src/main/resources/sounds/msg.wav").join();
        //URL url = this.getClass().getClassLoader().getResource("sounds/msg.wav");
        //AudioClip clip = Applet.newAudioClip(url);
        //clip.play();
    }

    public void delayTask(int delayHours, int delayMinutes){
        context.addAlarmTask(alarm, delayHours, delayMinutes);
    }
}
