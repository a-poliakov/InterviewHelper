package entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable
public class Interview {
    @DatabaseField (generatedId = true)
    private int idInterview;

    @DatabaseField (columnName = "idCandidate", foreignColumnName = "idCandidate", canBeNull = false, foreign = true)
    private Candidate idCandidate;

    @DatabaseField (columnName = "idInterviewer", foreignColumnName = "idInterviewer", canBeNull = false, foreign = true)
    private Interviewer idInterviewer;

    @DatabaseField (canBeNull = false)
    private Date Date;

    @DatabaseField (canBeNull = false)
    private String Result;

    @DatabaseField (canBeNull = false)
    private String Post;

    @ForeignCollectionField
    private ForeignCollection<Mark> marks;

    public Interview() {
    }

    public int getIdInterview() {
        return idInterview;
    }

    public void setIdInterview(int idInterview) {
        this.idInterview = idInterview;
    }

    public Candidate getIdCandidate() {
        return idCandidate;
    }

    public void setIdCandidate(Candidate idCandidate) {
        this.idCandidate = idCandidate;
    }

    public Interviewer getIdInterviewer() {
        return idInterviewer;
    }

    public void setIdInterviewer(Interviewer idInterviewer) {
        this.idInterviewer = idInterviewer;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    public ForeignCollection<Mark> getMarks() {
        return marks;
    }

    public void setMarks(ForeignCollection<Mark> marks) {
        this.marks = marks;
    }
}
