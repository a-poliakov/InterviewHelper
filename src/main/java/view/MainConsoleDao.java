package view;

import config.HelperFactory;
import entity.Interview;

import java.io.IOException;
import java.util.Date;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import util.DBUtil;

public class MainConsoleDao  {
    public static void main(String[] args) throws SQLException,IOException {
        DBUtil.exportDb("C:\\javaprj\\InterviewBD.db");
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