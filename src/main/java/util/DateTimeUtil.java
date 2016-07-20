package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {
    public static LocalDateTime parse (String date, String time){
        LocalDate partsOfDate = DateUtil.parse(date);
        int day = partsOfDate.getDayOfMonth();
        int month = partsOfDate.getMonth().getValue();
        int year = partsOfDate.getYear();
        String[] tokens = time.split(":");
        int hours = getIntField(tokens, 0);
        int minutes = getIntField(tokens, 1) ;
        int seconds = getIntField(tokens, 2);
        int totalSeconds = (hours * 60 + minutes) * 60 + seconds ;
        return LocalDateTime.of(year, month, day, (totalSeconds / 3600) % 24, (totalSeconds / 60) % 60, seconds % 60);
    }

    public static LocalTime parse(String time) {
        String[] tokens = time.split(":");
        int hours = getIntField(tokens, 0);
        int minutes = getIntField(tokens, 1) ;
        int seconds = getIntField(tokens, 2);
        int totalSeconds = (hours * 60 + minutes) * 60 + seconds ;
        return LocalTime.of((totalSeconds / 3600) % 24, (totalSeconds / 60) % 60, seconds % 60);
    }

    private static int getIntField(String[] tokens, int index) {
        return tokens.length <= index || tokens[index].isEmpty() ? 0 : Integer.parseInt(tokens[index]);
    }
}
