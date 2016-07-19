package util;
import java.lang.Exception;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mpmayorov on 18.07.2016.
 */
public class Validator {
    public static void checkFio (String fio)throws Exception{
        Pattern p = Pattern.compile("^[ a-zA-Zа-яА-Я\\.]+$");
        Matcher m = p.matcher(fio);
        if(!m.matches()){
            throw new Exception("Фамилия '"+ fio + "' содержит запрещенные символы.");
        }
    }
    public static void checkDate (String date)throws Exception{
        Pattern p = Pattern.compile("^[0-9]{1,2}[.][0-9]{1,2}[.][0-9]{4}$");
        Matcher m = p.matcher(date);
        if(!m.matches()){
            throw new Exception("Дата '" + date + "' указана не верно.");
        }
    }

}
