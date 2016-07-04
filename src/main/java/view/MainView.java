package view;

import config.DatabaseHelper;
import config.HelperFactory;
import entity.Interview;

import java.sql.SQLException;
import java.util.List;

public class MainView {
    public static void main(String[] args) throws SQLException {
        List<Interview> interviews = HelperFactory.getHelper().getInterviewsByCandidateFio("poly");
        for (Interview interview : interviews) {
            System.out.println(interview.getIdCandidate().getFio() + " " + interview.getPost());
        }
    }
}
