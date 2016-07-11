package entity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by mpmayorov on 04.07.2016.
 */
@DatabaseTable
public class Interviewer {
    @DatabaseField(generatedId = true)
    private int idInterviewer;
    @DatabaseField(canBeNull = false)
    private String fio;

    @ForeignCollectionField(foreignFieldName = "idInterviewer", eager = true)
    private Collection<Interview> interviews;

    @Override
    public String toString() {
        return fio;
    }

    public Interviewer() {
    }

    public int getIdInterviewer() {
        return idInterviewer;
    }

    public void setIdInterviewer(int idInterviewer) {
        this.idInterviewer = idInterviewer;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Collection<Interview> getInterviews() {
        return interviews;
    }

    public void setInterviews(Collection<Interview> interviews) {
        this.interviews = interviews;
    }
}
