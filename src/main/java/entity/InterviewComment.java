package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class InterviewComment {
    @DatabaseField(generatedId = true)
    private int idIC;
    
    @DatabaseField(canBeNull = false, foreign = true)
    private Interview interviewId;

    @DatabaseField
    private String experience;

    @DatabaseField
    private String recommendations;

    @DatabaseField
    private String lastWork;

    @DatabaseField
    private String comment;

    public InterviewComment() {
    }

    public int getIdIC() {
        return idIC;
    }

    public void setIdIC(int idIC) {
        this.idIC = idIC;
    }

    public Interview getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Interview interviewId) {
        this.interviewId = interviewId;
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
