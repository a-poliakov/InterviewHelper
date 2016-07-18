import util.Validator;
import org.junit.Test;

/**
 * Created by mpmayorov on 18.07.2016.
 */
public class ValidatorTest {
    //проверка fio
    @Test(expected = Exception.class)
    public void fioNumber() throws Exception {
        Validator.checkFio("за1за");
    }

    @Test(expected = Exception.class)
    public void fioEmptyString() throws Exception {
        Validator.checkFio("");
    }

    @Test(expected = Exception.class)
    public void fioBanChar() throws Exception {
        Validator.checkFio("1");
    }

    @Test
    public void fioSingleChar() throws Exception {
        Validator.checkFio("a");
    }

    @Test
    public void fioDot() throws Exception {
        Validator.checkFio("...");
    }

    @Test
    public void fioTestShortName() throws Exception {
        Validator.checkFio("Ветров А.Ю");
    }

    @Test
    public void fioFullName() throws Exception {
        Validator.checkFio("Анатолий Петрович Васичкин");
    }

    //Првоерка даты
    @Test
    public void dateFull() throws Exception {
        Validator.checkDate("05.12.2001");
    }
    @Test
    public void dateShort() throws Exception {
        Validator.checkDate("5.1.2001");
    }
    @Test(expected = Exception.class)
    public void dateBanChar() throws Exception {
        Validator.checkDate("1.12.2w01");
    }
    @Test(expected = Exception.class)
    public void dateShortYear() throws Exception {
        Validator.checkDate("1.12.201");
    }
    @Test(expected = Exception.class)
    public void dateEmpty() throws Exception {
        Validator.checkDate("");
    }
}
