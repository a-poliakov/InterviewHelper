package view;

import config.DatabaseHelper;
import config.HelperFactory;
import entity.Interview;
import java.util.Date;

import java.sql.SQLException;
import java.util.List;

public class MainConsoleDao {
    public static void main(String[] args) throws SQLException {
        System.out.println(HelperFactory.getHelper().getCategories().size());
    }
}