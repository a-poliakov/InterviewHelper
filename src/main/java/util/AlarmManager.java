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
    private java.util.Timer  timer = new Timer();

    public void start(){
        for (Alarm o : alarms) {
            AlarmTask task = new AlarmTask(o, this);
            timer.schedule(task, 0);
        }
    }

    // получение строки текущей даты в формате "дд.мм.гггг"
    // обращается к DateUtil
    public String getTodayDateString(){
        String curDate;
        LocalDate date = LocalDate.now();
        curDate = DateUtil.format(date);
        return curDate;
    }

    // заполнение коллекции alarms актуальными записями
    // TODO : добавить потом удаление старых записей
    public  void fillTodayAlarmList() throws SQLException {
        String curDate = getTodayDateString();
        List<Interview> interviews = HelperFactory.getHelper().getInterviewsByDate(curDate);
        for (Interview o : interviews){
            Alarm alarm = new Alarm(o.getIdCandidate().getFio(), o.getPost(), null, true);
            alarms.add(alarm);
        }
    }

    public void addAlarmTask(Alarm alarm, int delayHours, int delayMinutes) {

    }
}
