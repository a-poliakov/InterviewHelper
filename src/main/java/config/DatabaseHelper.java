package config;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import entity.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DatabaseHelper {
    // URL для подключения к БД
    private final String URL = "jdbc:sqlite:src\\main\\resources\\InterviewBD.db";
    // Подключение к БД
    private ConnectionSource connectionSource;
    //----------------------------------------------------------------------------
    // DAO для работы с сущностями
    private Dao<Candidate, Integer> candidateDao = null;
    private Dao<Category, Integer> categoryDao = null;
    private Dao<Interview, Integer> interviewDao = null;
    private Dao<InterviewComment, Integer> interviewCommentDao = null;
    private Dao<Interviewer, Integer> interviewerDao = null;
    private Dao<Mark, Integer> markDao = null;

    public DatabaseHelper() throws SQLException {
        connectionSource = new JdbcConnectionSource(URL);
        candidateDao = DaoManager.createDao(connectionSource,Candidate.class);
        categoryDao = DaoManager.createDao(connectionSource,Category.class);
        interviewDao = DaoManager.createDao(connectionSource,Interview.class);
        interviewCommentDao = DaoManager.createDao(connectionSource,InterviewComment.class);
        interviewerDao = DaoManager.createDao(connectionSource,Interviewer.class);
        markDao = DaoManager.createDao(connectionSource,Mark.class);
    }

    /*
     * Метод для получения всех интервью, в которых собеседовали нужного кандидата
     * @author Андрей Поляков
     * @param fio ФамилияИмяОтчество необходимого кандидата
     * @return List<Interview> все подходящие интервью
     */
    public List<Interview> getInterviewsByCandidateFio(String fio) throws SQLException {
        // первая таблица в запросе
        QueryBuilder<Interview, Integer> interviewQueryBuilder = interviewDao.queryBuilder();
        // присоединяемая таблица
        QueryBuilder<Candidate, Integer> candidateQueryBuilder = candidateDao.queryBuilder();
        // делаем выборку по полям присоединяемой таблицы
        candidateQueryBuilder.where().like("fio", fio + "%");
        // делаем left join
        interviewQueryBuilder.leftJoin(candidateQueryBuilder);
        // готово!
        // в итоге сконструирован запрос:
        // SELECT `interview`.* FROM `interview`
        // LEFT JOIN `candidate` ON `interview`.`idCandidate` = `candidate`.`idCandidate`
        // WHERE `candidate`.`fio` = 'polyakov'
        PreparedQuery<Interview> preparedQuery = interviewQueryBuilder.prepare();
        List<Interview> interviews = interviewDao.query(preparedQuery);
        return interviews;
    }

    public List<Interview> getInterviewsByDate(Date date) throws SQLException {
        QueryBuilder<Interview, Integer> interviewQueryBuilder = interviewDao.queryBuilder();
        interviewQueryBuilder.where().eq("Date", date);
        PreparedQuery<Interview> preparedQuery = interviewQueryBuilder.prepare();
        List<Interview> interviews = interviewDao.query(preparedQuery);
        return interviews;
    }

    public List<Interview> getInterviewsByPost(String post) throws SQLException {
        QueryBuilder<Interview, Integer> interviewQueryBuilder = interviewDao.queryBuilder();
        interviewQueryBuilder.where().eq("Post", post);
        PreparedQuery<Interview> preparedQuery = interviewQueryBuilder.prepare();
        List<Interview> interviews = interviewDao.query(preparedQuery);
        return interviews;
    }
    //Получить интервью по Id
    public Interview getInterviewById(int id) throws SQLException {
        QueryBuilder<Interview, Integer> interviewQueryBuilder = interviewDao.queryBuilder();
        interviewQueryBuilder.where().eq("idInterview", id);
        PreparedQuery<Interview> preparedQuery = interviewQueryBuilder.prepare();
        List<Interview> interviews = interviewDao.query(preparedQuery);
        return interviews.get(0);
    }
    public List<Category> getCategories() throws SQLException {
        return categoryDao.queryForAll();
    }
    public List<Candidate> getCandidates() throws SQLException {
        return candidateDao.queryForAll();
    }
    public Candidate getCandidateById(int id) throws SQLException {
        QueryBuilder<Candidate, Integer> candidateQueryBuilder = candidateDao.queryBuilder();
        candidateQueryBuilder.where().eq("idCandidate", id);
        PreparedQuery<Candidate> preparedQuery = candidateQueryBuilder.prepare();
        List<Candidate> candidates = candidateDao.query(preparedQuery);
        return candidates.get(0);
    }
  /*  public Interview createInterview(Date Dat)  throws SQLException{
        Interview interview = new Interview();
        interview.setDate(Dat);
        Candidate candidate = new Candidate();
        Interviewer interviewer  = new Interviewer();
        candidate.setIdCandidate(4);
        interviewer.setIdInterviewer(5);
        interview.setIdCandidate(candidate);
        interview.setIdInterviewer(interviewer);
        interview.setResult("Да");
        interview.setPost("Директор 1");
        interviewDao.create(interview);
        return interview;
    }
*/

}
