package view;

import config.HelperFactory;
import java.util.Date;

import java.sql.SQLException;
import java.util.List;

public class MainConsoleDao {
    public static void main(String[] args) throws SQLException {
        HelperFactory.getHelper().addCandidate("Проверка строк","21.03.2016" , "No");

        for(Object cat:HelperFactory.getHelper().getInterviewMarksAll(12))
            System.out.println(cat);
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