package view;

import config.HelperFactory;
import java.util.Date;

import java.sql.SQLException;
import java.util.List;

public class MainConsoleDao {
    public static void main(String[] args) throws SQLException {
        HelperFactory.getHelper().addCandidate("Гендальф", new Date((new Date()).getTime()), "No");

        for(Object cat:HelperFactory.getHelper().getCandidates())
            System.out.println(cat);
    }
//    public static void ()
//    {
//
//    }
}