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
    private Date date;

    @DatabaseField (canBeNull = false)
    private String result;

    @DatabaseField (canBeNull = false)
    private String post;

    @ForeignCollectionField(foreignFieldName = "idInterview", eager = true)
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
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public ForeignCollection<Mark> getMarks() {
        return marks;
    }

    public void setMarks(ForeignCollection<Mark> marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Interview{" +
                "idInterview=" + idInterview +
                ", idCandidate=" + idCandidate +
                ", idInterviewer=" + idInterviewer +
                ", Date=" + date +
                ", Result='" + result + '\'' +
                ", Post='" + post +
        '}';
    }
}
