package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class InterviewComment {
    @DatabaseField(generatedId = true)
    int idIC;

    @DatabaseField(canBeNull = false)
    Interview interview;

    @DatabaseField
    String experience;

    @DatabaseField
    String recommendations;

    @DatabaseField
    String lastWork;

    @DatabaseField
    String comment;

    public InterviewComment() {
    }

    public int getIdIC() {
        return idIC;
    }

    public void setIdIC(int idIC) {
        this.idIC = idIC;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
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
}
