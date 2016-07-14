package model;

import java.util.Date;

public class Alarm {
    private String info;
    private Date alarmDate;
    private boolean isShown;

    public Alarm(String info, Date alarmDate, boolean isShown) {
        this.info = info;
        this.alarmDate = alarmDate;
        this.isShown = isShown;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }
}
