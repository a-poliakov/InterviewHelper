package entity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.sun.java.swing.plaf.windows.WindowsTreeUI;

import java.util.Collection;

/**
 * Created by mpmayorov on 04.07.2016.
 */
@DatabaseTable
public class Interviewer {
    @DatabaseField(generatedId = true)
    private int idInterviewer;
    @DatabaseField(canBeNull = false)
    private String FIO;

    @ForeignCollectionField(foreignFieldName = "idInterviewer", eager = true)
    private Collection<Interview> interviews;

    public Interviewer() {
    }

    public int getIdInterviewer() {
        return idInterviewer;
    }

    public void setIdInterviewer(int idInterviewer) {
        this.idInterviewer = idInterviewer;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public Collection<Interview> getInterviews() {
        return interviews;
    }

    public void setInterviews(Collection<Interview> interviews) {
        this.interviews = interviews;
    }
}
