package view;

import config.HelperFactory;
import java.util.Date;

import java.sql.SQLException;
import java.util.List;

public class MainConsoleDao {
    public static void main(String[] args) throws SQLException {
        System.out.println(HelperFactory.getHelper().getCategories().size());
        //HelperFactory.getHelper().addInterview(1, 2, new Date((new Date()).getTime()), "Принят", "Генерал");
        for(Object cat:HelperFactory.getHelper().getInterview())
            System.out.println(cat);
        //HelperFactory.getHelper().delInterviewById(11);
    }
}