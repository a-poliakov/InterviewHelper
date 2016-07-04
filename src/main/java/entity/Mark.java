package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by momelnikov on 04.07.2016.
 */
@DatabaseTable
public class Mark {
    @DatabaseField(generatedId = true)
    private int idMark;

    @DatabaseField(canBeNull = false)
    private double value;

    @DatabaseField(canBeNull = false , foreign = true)
    private Category idCategory;

    @DatabaseField(canBeNull = false, foreign = true)
    private Interview idInterview;

    public Mark() {
    }

    public int getIdMark() {
        return idMark;
    }

    public double getValue() {
        return value;
    }

    public Category getIdCategory() {
        return idCategory;
    }

    public Interview getIdInterview() {
        return idInterview;
    }

    public void setIdMark(int idMark) {
        this.idMark = idMark;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setIdCategory(Category idCategory) {
        this.idCategory = idCategory;
    }

    public void setIdInterview(Interview idInterview) {
        this.idInterview = idInterview;
    }
}
