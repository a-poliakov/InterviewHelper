package config;

import java.sql.SQLException;

public class HelperFactory {
    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper getHelper() throws SQLException {
        return databaseHelper == null ? new DatabaseHelper() : databaseHelper;
    }
}
