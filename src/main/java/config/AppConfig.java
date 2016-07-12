package config;

public interface AppConfig {
    // TODO: 11.07.2016 Конфиги для приложения: пути к файлам разметки, бд и т.д.
    // Файлы разметки
    String FXML_MAIN_URL = "views/main_view.fxml";
    String FXML_ADD_INTERVIEW_DLG_URL = "views/add_interview_dlg.fxml";
    String FXML_ADD_INTERVIEWER_DLG_URL = "views/add_interviewer_dlg.fxml";
    String FXML_ADD_CANDIDATE_DLG_URL = "views/add_candidate_dlg.fxml";
    String FXML_EDIT_CATEGORY_DLG_URL = "views/edit_categories_dlg.fxml";
    // База данных
    String DATABASE_URL = "jdbc:sqlite:InterviewBD.db";
}
