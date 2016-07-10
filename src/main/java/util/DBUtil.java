package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Андрей on 07.07.2016.
 */
public class DBUtil {
    public static void initDB() throws IOException {
        File dataBase = new File("InterviewBD.db");
        if(!dataBase.exists())
        {
            File emptyDataBase = new File("src\\main\\resources\\InterviewBD.db");
            Files.copy(emptyDataBase.toPath(), dataBase.toPath());
        }
    }
}
