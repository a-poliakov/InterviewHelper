package model;

import java.util.Date;

public class Alarm {
    private String fio;
    private String post;
    private Date alarmDate;
    private boolean isShown;

    public Alarm(String fio, String post, Date alarmDate, boolean isShown) {
        this.fio = fio;
        this.post = post;
        this.alarmDate = alarmDate;
        this.isShown = isShown;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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
