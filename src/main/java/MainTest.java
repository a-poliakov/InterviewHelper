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
        for(Category cat:db.getCategories())
            System.out.println(cat);
        db.delCategoryById(2);
        for(Category cat:db.getCategories())
            System.out.println(cat);
    }

}
