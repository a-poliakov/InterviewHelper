package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

/**
 * Created by mpmayorov on 04.07.2016.
 */
@DatabaseTable
public class Candidate {
    @DatabaseField(generatedId = true)
    private int idCandidate;

    @DatabaseField(canBeNull = false)
    private String FIO;

    @DatabaseField(canBeNull = false)
    private Date BornDate;

    @DatabaseField(canBeNull = false)
    private String Banned;

    public Candidate() {
    }

    public int getIdCandidate() {
        return idCandidate;
    }

    public void setIdCandidate(int idCandidate) {
        this.idCandidate = idCandidate;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public Date getBornDate() {
        return BornDate;
    }

    public void setBornDate(Date bornDate) {
        BornDate = bornDate;
    }

    public String getBanned() {
        return Banned;
    }

    public void setBanned(String banned) {
        Banned = banned;
    }

}
