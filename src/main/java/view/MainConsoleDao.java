package view;

import config.HelperFactory;
import entity.Interview;

import java.util.Date;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class MainConsoleDao {
    public static void main(String[] args) throws SQLException {
        System.out.print(HelperFactory.getHelper().getCountOfCandidate());
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