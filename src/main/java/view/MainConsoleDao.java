package view;

import config.HelperFactory;
import java.util.Date;

import java.sql.SQLException;
import java.util.List;

public class MainConsoleDao {
    public static void main(String[] args) throws SQLException {
        HelperFactory.getHelper().editCategory(3, "Fyne");
    }
//    public static void ()
//    {
//
//    }
    //Все критерии, но где нет оценок ничего.
//    List<CategoryRow> getInterviewMarks(int idInterview){
//
//    }
}