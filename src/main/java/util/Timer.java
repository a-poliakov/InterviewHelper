package util;

import model.Alarm;

import java.util.ArrayList;
import java.util.List;

public class Timer extends Thread{
    private volatile List<Alarm> alarms = new ArrayList<>();
    java.util.Timer ticTac;

    @Override
    public synchronized void start() {
        super.start();
    }

    public List<Alarm> getTodayAlarms(){
        throw new NullPointerException();
    }


}
