/**
 * Created by ZVER on 05.07.2016.
 */
import com.j256.ormlite.dao.*;
import entity.*;
import config.*;
import java.sql.SQLException;

public class MainTest {
    public static void main(String[] args) throws SQLException {
        DatabaseHelper db = new DatabaseHelper();
        db.addCategory("Тестирование");
        for(Candidate cat:db.getCandidates())
            System.out.println(cat);
    }

}
