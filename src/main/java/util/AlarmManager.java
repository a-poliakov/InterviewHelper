package util;

import config.HelperFactory;
import entity.Interview;
import javafx.application.Platform;
import model.Alarm;
import view.AlarmTemplateBuilder;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.Timer;


public class AlarmManager{
    private  List<Alarm> alarms = new ArrayList<>();
    private java.util.Timer timer = new Timer();

    public void start(){
        for (Alarm o : alarms) {
            AlarmTask task = new AlarmTask(o, this);
            timer.schedule(task, 0);
        }
    }

    // получение строки текущей даты в формате "дд.мм.гггг"
    // обращается к DateUtil
    public String getTodayDateString() throws Exception {
        String curDate;
        LocalDate date = LocalDate.now();
        curDate = DateUtil.format(date);
        return curDate;
    }

    // заполнение коллекции alarms актуальными записями
    // TODO : добавить потом удаление старых записей
    public  void updateTodayAlarmList() throws Exception {
        String curDate = getTodayDateString();
        List<Interview> interviews = HelperFactory.getHelper().getInterviewsByDate(curDate);
        alarms.clear();
        for (Interview o : interviews){
            Alarm alarm = new Alarm(o.getIdCandidate().getFio(), o.getPost(), null, true);
            alarms.add(alarm);
        }
    }

    public void addAlarmTask(Alarm alarm, int delayHours, int delayMinutes) {
        AlarmTask task = new AlarmTask(alarm, this);
        long delay = delayHours * ConstantManager.MILLISECOND_IN_HOUR + delayMinutes * ConstantManager.MILLISECOND_IN_MINUTE;
        timer.schedule(task, delay);
    }
}
