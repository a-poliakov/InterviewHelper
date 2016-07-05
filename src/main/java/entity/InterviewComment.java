package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class InterviewComment {
    @DatabaseField(generatedId = true)
    private int idIC;

    @DatabaseField (columnName = "idInterview", foreignColumnName = "idInterview", canBeNull = false, foreign = true)
    private Interview idInterview;

    @DatabaseField
    private String experience;

    @DatabaseField
    private String recommendations;

    @DatabaseField
    private String lastWork;

    @DatabaseField
    private String comment;

    public int getIdIC() {
        return idIC;
    }

    public void setIdIC(int idIC) {
        this.idIC = idIC;
    }

    public Interview getIdInterview() {
        return idInterview;
    }

    public void setIdInterview(Interview idInterview) {
        this.idInterview = idInterview;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getLastWork() {
        return lastWork;
    }

    public void setLastWork(String lastWork) {
        this.lastWork = lastWork;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public InterviewComment() {
    }

}
