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
        if(interviews.size() == 0)
            return null;
        return interviews.get(0);
    }
    public List<Category> getCategories() throws SQLException {
        return categoryDao.queryForAll();
    }
    public List<Candidate> getCandidates() throws SQLException {
        return candidateDao.queryForAll();
    }
    public List<Interviewer> getInterviewers() throws SQLException {
        return interviewerDao.queryForAll();
    }
    public Candidate getCandidateById(int id) throws SQLException {
        QueryBuilder<Candidate, Integer> candidateQueryBuilder = candidateDao.queryBuilder();
        candidateQueryBuilder.where().eq("idCandidate", id);
        PreparedQuery<Candidate> preparedQuery = candidateQueryBuilder.prepare();
        List<Candidate> candidates = candidateDao.query(preparedQuery);
        if(candidates.size() == 0)
            return null;
        return candidates.get(0);
    }
    public Interviewer getInterviewerById(int id) throws SQLException {
        QueryBuilder<Interviewer, Integer> interviewerQueryBuilder = interviewerDao.queryBuilder();
        interviewerQueryBuilder.where().eq("idInterviewer", id);
        PreparedQuery<Interviewer> preparedQuery = interviewerQueryBuilder.prepare();
        List<Interviewer> interviewers = interviewerDao.query(preparedQuery);
        if(interviewers.size() == 0)
            return null;
        return interviewers.get(0);
    }
//  public void addInterview()  throws SQLException{
//      Interview interview = new Interview();
//      interview.setDate(new Date(123123));
//      interview.setPost("director");
//      interview.setResult("yes");
//      interview.setIdInterviewer(getInterviewerById(1));
//      interview.setIdCandidate(getCandidateById(1));
//      interviewDao.create(interview);
//    }
    public boolean addInterviewer(String fio)  throws SQLException{
        Interviewer interviewer = new Interviewer();
        interviewer.setFIO(fio);
        // TODO: 05.07.2016
        interviewerDao.create(interviewer);
        return true;
    }

}
