package view;

import config.HelperFactory;
import java.util.Date;

import java.sql.SQLException;
import java.util.List;

public class MainConsoleDao {
    public static void main(String[] args) throws SQLException {


        for(Object cat:HelperFactory.getHelper().getInterviewsByCandidateFio("Мельников"))
            System.out.println(cat);
    }
//    public static void ()
//    {
//
//    }
}