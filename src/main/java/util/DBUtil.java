package util;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import entity.*;
import config.AppConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.nio.*;


public class DBUtil {
    public static void createDbIfNotExist() throws SQLException{
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(AppConfig.DATABASE_URL);
            createDbIfNotExist(connectionSource);
            connectionSource.close();
        } catch(Exception e){

        }
    }
    public static void createDbIfNotExist(ConnectionSource connectionSource) throws SQLException{
        if (connectionSource == null)
        {
            createDbIfNotExist();
        } else {
        TableUtils.createTableIfNotExists(connectionSource, Candidate.class);
        TableUtils.createTableIfNotExists(connectionSource, Category.class);
        TableUtils.createTableIfNotExists(connectionSource, Interview.class);
        TableUtils.createTableIfNotExists(connectionSource, InterviewComment.class);
        TableUtils.createTableIfNotExists(connectionSource, Interviewer.class);
        TableUtils.createTableIfNotExists(connectionSource, Mark.class);
        }
    }
    public static void importDb(String url) throws IOException{
        File database = new File("InterviewBD.db");
        File newDatabase = new File(url + "\\InterviewBD.db");
        try {
            Files.copy(database.toPath(), newDatabase.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {}
    }

    public static void exportDb(String url) throws IOException{
        File newDatabase = new File("InterviewBD.db");
        File database = new File(url);
        try {
            Files.copy(database.toPath(), newDatabase.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {}
    }


}
