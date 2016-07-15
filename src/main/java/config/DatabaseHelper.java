package config;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.spring.DaoFactory;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dao.impl.CandidateDao;
import entity.*;
import model.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import util.DBUtil;

public class DatabaseHelper {
    // URL для подключения к БД
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

        connectionSource = new JdbcConnectionSource(AppConfig.DATABASE_PREFIX + AppConfig.DATABASE_FILENAME);
        candidateDao = DaoManager.createDao(connectionSource,Candidate.class);
        categoryDao = DaoManager.createDao(connectionSource,Category.class);
        interviewDao = DaoManager.createDao(connectionSource,Interview.class);
        interviewCommentDao = DaoManager.createDao(connectionSource,InterviewComment.class);
        interviewerDao = DaoManager.createDao(connectionSource,Interviewer.class);
        markDao = DaoManager.createDao(connectionSource,Mark.class);
        DBUtil.createDbIfNotExist(connectionSource);

    }

    public List<Interview> getInterviewsByCandidateFioAndDateAndPost(String fio, String post, String date) throws SQLException {
        QueryBuilder<Interview, Integer> interviewQueryBuilder = interviewDao.queryBuilder();
        QueryBuilder<Candidate, Integer> candidateQueryBuilder = candidateDao.queryBuilder();
        candidateQueryBuilder.where().like("fio","%" + fio + "%");
        interviewQueryBuilder.leftJoin(candidateQueryBuilder);
        interviewQueryBuilder.where().like("Date", "%" + date + "%").and().like("Post","%" + post + "%");
        PreparedQuery<Interview> preparedQuery = interviewQueryBuilder.prepare();
        List<Interview> interviews = interviewDao.query(preparedQuery);
        return interviews;
    }

    /*
     * Метод для фильтрации интервью по ФИО кандидата
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
        candidateQueryBuilder.where().like("fio","%" + fio + "%");
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

    /**
     * Метод для фильтрации интервью по дате
     * @param date строкка выражающая частично определенную дату
     * @return List<Interview> все подходящие интервью
     * @throws SQLException
     */
    public List<Interview> getInterviewsByDate(String date) throws SQLException {
        QueryBuilder<Interview, Integer> interviewQueryBuilder = interviewDao.queryBuilder();
        interviewQueryBuilder.where().like("Date", "%" + date + "%");
        PreparedQuery<Interview> preparedQuery = interviewQueryBuilder.prepare();
        List<Interview> interviews = interviewDao.query(preparedQuery);
        return interviews;
    }

    /**
     * Метод для фильтрации интервью по должноти, на которую претендуют
     * @param post должность на котурую претендуют
     * @return List<Interview> все подходящие интервью
     * @throws SQLException
     */
    public List<Interview> getInterviewsByPost(String post) throws SQLException {
        QueryBuilder<Interview, Integer> interviewQueryBuilder = interviewDao.queryBuilder();
        interviewQueryBuilder.where().like("Post","%" + post + "%");
        PreparedQuery<Interview> preparedQuery = interviewQueryBuilder.prepare();
        List<Interview> interviews = interviewDao.query(preparedQuery);
        return interviews;
    }

<<<<<<< HEAD
    //Получить по полям
=======
    /**
     * Метод получения из БД оценки по интервью и критерию
     * @param idInterview айди нужного интервью
     * @param categoryName Имя критерия
     * @return Mark оценка или null
     * @throws SQLException
     */
>>>>>>> ff572165da6129c51261d0bb8da290372a64becd
    public Mark getMarkByInterviewAndCategory(int idInterview,String categoryName)throws SQLException{
        List<Mark> marks = getInterviewMarks(idInterview);
        for(Mark mark: marks)
        {
            if(mark.getIdCategory().getName().equals(categoryName))
            {
                return mark;
            }
        }
        // TODO: 07.07.2016 Костыль
        return null;
    }

    /**
     * Метод, получающий из БД кандидата по ФИО или созает нового (если не найден)
     * @param fio ФИО для поиска
     * @return Candidate искомый или новый кандидат
     * @throws SQLException
     */
    public Candidate getCandidateByFio(String fio)throws SQLException{
        List<Candidate> candidates = candidateDao.queryForAll();
        for(Candidate candidate: candidates)
        {
            if(candidate.getFio().equals(fio))
            {
                return candidate;
            }
        }
        // TODO: 07.07.2016 Костыль создания новых пользователей
        return addCandidate(fio, "01.02.1975", "-");
    }

    /**
     *  Метод, получающий из БД рекрутера по ФИО или создающий нового (если не найден)
     * @param fio ФИО для поиска
     * @return Interviewer найденный или новый
     * @throws SQLException
     */
    public Interviewer getInterviewerByFio(String fio) throws SQLException{
        //если не нашел, то создаст нового
        List<Interviewer> interviewers = interviewerDao.queryForAll();
        for(Interviewer interviewer: interviewers)
        {
            if(interviewer.getFio().equals(fio))
            {
                return interviewer;
            }
        }
        return addInterviewer(fio);
    }

    /**
     * Метод получающий из БД критерий по названию
     * @param name название критерия
     * @return Category критерий или null
     * @throws SQLException
     */
    public Category getCategoryByName(String name) throws SQLException{
        //если не нашел, то создаст нового
        List<Category> categoryes = categoryDao.queryForAll();
        for(Category category: categoryes)
        {
            if(category.getName().equals(name))
            {
                return category;
            }
        }
        return null;
    }
    //Получить по Id

    /**
     * Метод получает из БД комментарий к интервью по айди интервью
     * @param id фади интервью
     * @return комментарий или null
     * @throws SQLException
     */
    public InterviewComment getInterviewCommentByIdInterview(int id)throws SQLException{
        List<InterviewComment> interviewComments = interviewCommentDao.queryForAll();
        for(InterviewComment ic: interviewComments)
        {
            if(ic.getIdInterview().getIdInterview() == id)
            {
                return ic;
            }
        }
        return null;
    }

    /**
     * Метод, получающий из БД все оценки по критерия из некоторого интервью
     * @param idInterview айди нужного интервью
     * @return List<CategoryRow> Список в виде название категории, оцентка
     * @throws SQLException
     */
    public List<CategoryRow> getInterviewMarksAll(int idInterview)throws SQLException  {
        List<Category> categories = getCategories();
        List<Mark> marks = getInterviewMarks(idInterview);
        List<CategoryRow> categoryRows = new ArrayList<CategoryRow>();
        for(Category cat:categories) {
            CategoryRow categoryRow = new CategoryRow(cat, 0.0);
            for(Mark mark:marks)
            {
                if(mark.getIdCategory().getIdCategory() == cat.getIdCategory())
                {
                    categoryRow.setValue(mark.getValue());
                }
            }
            categoryRows.add(categoryRow);
        }
        return categoryRows;
        // TODO: 06.07.2016 отсортировать лексикографически
    }

    /**
     * Метод получающий из БД интервью по id
     * @param id то самое id
     * @return нужное интервью или null
     * @throws SQLException
     */
    public Interview getInterviewById(int id) throws SQLException {
        QueryBuilder<Interview, Integer> interviewQueryBuilder = interviewDao.queryBuilder();
        interviewQueryBuilder.where().eq("idInterview", id);
        PreparedQuery<Interview> preparedQuery = interviewQueryBuilder.prepare();
        List<Interview> interviews = interviewDao.query(preparedQuery);
        if(interviews.size() == 0)
            return null;
        return interviews.get(0);
    }

    /**
     * Метод получающий из БД кандидата по id или создающий нового (если не найден)
     * @param id тот самый id
     * @return нужный или новый кандидат
     * @throws SQLException
     */
    public Candidate getCandidateById(int id) throws SQLException {
        QueryBuilder<Candidate, Integer> candidateQueryBuilder = candidateDao.queryBuilder();
        candidateQueryBuilder.where().idEq(id);
        PreparedQuery<Candidate> preparedQuery = candidateQueryBuilder.prepare();
        List<Candidate> candidates = candidateDao.query(preparedQuery);
        if(candidates.size() == 0)
            return addCandidate("empty", "01.01.2013", "-");
        return candidates.get(0);
    }

    /**
     * Метод получающий из БД критерий по id
     * @param id тот самый id
     * @return критерий
     * @throws SQLException
     */
    public Category getCategoryById(int id) throws SQLException {
        QueryBuilder<Category, Integer> query = categoryDao.queryBuilder();
        query.where().idEq(id);
        PreparedQuery<Category> preparedQuery = query.prepare();
        List<Category> categories = categoryDao.query(preparedQuery);
        if(categories.size() == 0)
            return null;
        return categories.get(0);
    }

    /**
     * Метод получающий из БД рекрутера по id или создающий нового (если не найден)
     * @param id тот самый id
     * @return рекрутер или пустой рекрутер
     * @throws SQLException
     */
    public Interviewer getInterviewerById(int id) throws SQLException {
        QueryBuilder<Interviewer, Integer> interviewerQueryBuilder = interviewerDao.queryBuilder();
        interviewerQueryBuilder.where().idEq(id);
        PreparedQuery<Interviewer> preparedQuery = interviewerQueryBuilder.prepare();
        List<Interviewer> interviewers = interviewerDao.query(preparedQuery);
        if(interviewers.size() == 0)
            return addInterviewer("empty");
        return interviewers.get(0);
    }

    /**
     * Метод получающий все оценки из БД по интервью
     * @param idInterview id интервью
     * @return List <Mark> лист оценок
     * @throws SQLException
     */
    private List <Mark> getInterviewMarks(int idInterview)throws SQLException{
        QueryBuilder<Mark, Integer> markIntegerQueryBuilder = markDao.queryBuilder();
        markIntegerQueryBuilder.where().eq("idInterview", idInterview);
        PreparedQuery<Mark> preparedQuery = markIntegerQueryBuilder.prepare();
        List<Mark> marks = markDao.query(preparedQuery);
        return marks;
    }
    //Получить всех

    /**
     * Метод получающий количество интервью в БД
     * @return колво интервью
     * @throws SQLException
     */
    public long getCountOfInterview()throws SQLException {
        long countOfInterview = interviewDao.countOf();
        return countOfInterview;
    }

    /**
     * Метод получающий количество кандидатов в БД
     * @return колво кандидатов
     * @throws SQLException
     */
    public long getCountOfCandidate()throws SQLException {
        long countOfCandidate = candidateDao.countOf();
        return countOfCandidate;
    }

    /**
     * Метод, получающий все критерии из БД
     * @return List<Category>  список критериев
     * @throws SQLException
     */
    public List<Category> getCategories() throws SQLException {
        return categoryDao.queryForAll();
    }

    /**
     * Метод, получающий всех кандидатов из БД
     * @return List<Candidate> список кандидатов
     * @throws SQLException
     */
    public List<Candidate> getCandidates() throws SQLException {
        return candidateDao.queryForAll();
    }

    /**
     *  Метод, получающий все интервью из БД
     * @return  List<Interview> список кандидатов
     * @throws SQLException
     */
    public List<Interview> getInterview() throws SQLException{
        return interviewDao.queryForAll();
    }

    /**
     * Метод, получающий всех рекрутеров из БД
     * @return List<Interviewer> список рекрутеров
     * @throws SQLException
     */
    public List<Interviewer> getInterviewers() throws SQLException {
        return interviewerDao.queryForAll();
    }

    /**
     * Добавляет оценки в интервью 
     * @param idInterview
     * @param marks
     * @throws SQLException
     */
    public void addInterviewMarks(int idInterview, List<CategoryRow> marks) throws SQLException{
        for(CategoryRow cat:marks)
        {
            if(cat.getValue() != 0)
            {
                addMark(cat.getCategory().getIdCategory(), idInterview, cat.getValue());
            }
        }
    }
    public Interview addInterview(String name,String bornDate, String interviewer, String interviewDate, String result, String post)  throws SQLException{
        Interview interview = new Interview();
        Candidate candidate = getCandidateByFio(name);
        candidate.setBornDate(bornDate);
        candidateDao.createOrUpdate(candidate);
        interview.setIdCandidate(candidate);
        interview.setIdInterviewer(getInterviewerByFio(interviewer));
        interview.setDate(interviewDate);
        interview.setResult(result);
        interview.setPost(post);
        interviewDao.create(interview);
       return interview;
    }
    public Interviewer addInterviewer(String fio)  throws SQLException{
        Interviewer interviewer = new Interviewer();
        interviewer.setFio(fio);
        // TODO: 05.07.2016 Что делать при неудачной вставке? Исключение или возвращать false?
        interviewerDao.create(interviewer);
        return interviewer;
    }
    public Category addCategory(String name)  throws SQLException{
        Category category = new Category();
        category.setName(name);
        categoryDao.create(category);
        return category;
    }
    public Mark addMark(int idCategory, int idInterview, double value)  throws SQLException{
        //Перед добавлением оценки, убедись, что создано интервью!
        Mark mark = new Mark();
        mark.setIdCategory(getCategoryById(idCategory));
        mark.setIdInterview(getInterviewById(idInterview));
        mark.setValue(value);
        markDao.create(mark);
        return mark;
    }
    public InterviewComment addOrEditInterviewComment(int idInterview, String experience, String recommendations, String lastWork, String comment)throws SQLException{
        InterviewComment iCom = getInterviewCommentByIdInterview(idInterview);
        if (iCom == null) {
            iCom = new InterviewComment();
            iCom.setIdInterview(getInterviewById(idInterview));
        }
        iCom.setExperience(experience);
        iCom.setRecommendations(recommendations);
        iCom.setLastWork(lastWork);
        iCom.setComment(comment);
        interviewCommentDao.createOrUpdate(iCom);
        return iCom;
    }
    public Candidate addCandidate(String fio, String date, String banned)  throws SQLException{
        Candidate candidate = new Candidate();
        candidate.setFio(fio);
        candidate.setBornDate(date);
        candidate.setBanned(banned);
        candidateDao.create(candidate);
        return candidate;
    }
    //Удалить
    public void delCategoryById(int id)  throws SQLException{
        Category category = getCategoryById(id);
        QueryBuilder<Mark, Integer> markIntegerQueryBuilder = markDao.queryBuilder();
        markIntegerQueryBuilder.where().eq("idCategory", category);
        PreparedQuery<Mark> preparedQuery = markIntegerQueryBuilder.prepare();
        List<Mark> marks = markDao.query(preparedQuery);
        markDao.delete(marks);
        categoryDao.delete(category);
    }
    public void delInterviewById(int id)  throws SQLException{
        Interview interview = getInterviewById(id);
        QueryBuilder<InterviewComment, Integer> query = interviewCommentDao.queryBuilder();
        query.where().eq("idInterview", id);
        PreparedQuery<InterviewComment> preparedQuery = query.prepare();
        List<InterviewComment> interviewComment = interviewCommentDao.query(preparedQuery);
        if(interviewComment.size() != 0){
            interviewCommentDao.delete(interviewComment.get(0));
        }
        for(Mark mark:interview.getMarks()){
            markDao.delete(mark);
        }
        interviewDao.delete(interview);
    }
    public void delCandidateById(int id)  throws SQLException{
        Candidate candidate = getCandidateById(id);
        candidateDao.delete(candidate);
    }
    //Редактировать
    public Interview editOrAddInterview(int idInterview,String interviewDate, int idCandidate, String candidateFio, String bornDate, int idInterviewer, String interviewerFio, String result, String post, List<CategoryRow> marks) throws SQLException    {
        Candidate candidate = getCandidateById(idCandidate);
        candidate.setFio(candidateFio);
        candidate.setBornDate(bornDate);
        candidateDao.createOrUpdate(candidate);
        Interviewer interviewer = getInterviewerById(idInterviewer);
        interviewer.setFio(interviewerFio);
        interviewerDao.createOrUpdate(interviewer);
        Interview interview = getInterviewById(idInterview);
        if(interview == null)
        {
            interview = new Interview();
        }
        interview.setDate(interviewDate);
        interview.setIdInterviewer(interviewer);
        interview.setIdCandidate(candidate);
        interview.setResult(result);
        interview.setPost(post);
        interviewDao.createOrUpdate(interview);
        editInterviewMarks(interview.getIdInterview(), marks);
        return interview;
    }
    public void editInterview(int idInterview, String bornDate, String interviewDate, String result, String post, List<CategoryRow> marks)  throws SQLException{
        //if(idInterview == 0) новое
        //public void editOrAddInterview(idInterview, idCandidate, fio, bornDate, idInterviewer, fio, String result, String post, List<CategoryRow> marks)
        Interview interview = getInterviewById(idInterview);
        Candidate candidate = interview.getIdCandidate();
        candidate.setBornDate(bornDate);
        candidateDao.createOrUpdate(candidate);
        editInterviewDate(idInterview, interviewDate);
        editInterviewResult(idInterview, result);
        editInterviewPost(idInterview, post);
        editInterviewMarks(idInterview, marks);
    }

    public void editInterviewMarks(int idInterview, List<CategoryRow> marks) throws SQLException{
        for(CategoryRow cat:marks)
        {
            if(cat.getValue() != 0)
            {
                editMark(idInterview, cat.getCategory().getIdCategory(),  cat.getValue());
            }
        }
    }
    public void editMark(int idInterview, int idCategory, double value)throws SQLException {
        Mark mark = getMarkByInterviewAndCategory(idInterview, getCategoryById(idCategory).getName());
        if(mark == null)
        {
            addMark(idCategory,idInterview, value);
            return ;
        }
        mark.setValue(value);
        markDao.createOrUpdate(mark);
    }
    public void editCategory(int id, String name)throws SQLException{
        Category cat = getCategoryById(id);
        cat.setName(name);
        categoryDao.createOrUpdate(cat);
    }
    public void editInterviewDate(int idInterview, String date)throws SQLException{
        Interview interview = getInterviewById(idInterview);
        interview.setDate(date);
        interviewDao.createOrUpdate(interview);
    }
    public void editInterviewResult(int idInterview, String result)throws SQLException{
        Interview interview = getInterviewById(idInterview);
        interview.setResult(result);
        interviewDao.createOrUpdate(interview);
    }
    public void editInterviewPost(int idInterview, String post)throws SQLException{
        Interview interview = getInterviewById(idInterview);
        interview.setPost(post);
        interviewDao.createOrUpdate(interview);
    }
    public void editInterviewInterviewer(int idInterview, int idInterviewer)throws SQLException{
        Interview interview = getInterviewById(idInterview);
        interview.setIdInterviewer(getInterviewerById(idInterviewer));
        interviewDao.createOrUpdate(interview);
    }
    public void editCandidate(String fio,String date,String ban) throws SQLException {
        Candidate candidate = getCandidateByFio(fio);
        candidate.setFio(fio);
        candidate.setBornDate(date);
        candidate.setBanned(ban);
        candidateDao.createOrUpdate(candidate);
    }

}
