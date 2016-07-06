package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import config.HelperFactory;

import java.sql.SQLException;

/**
 * Created by momelnikov on 04.07.2016.
 */
@DatabaseTable
public class Mark {
    @DatabaseField(generatedId = true)
    private int idMark;

    @DatabaseField(canBeNull = false)
    private double value;

    @DatabaseField (columnName = "idCategory", foreignColumnName = "idCategory", canBeNull = false, foreign = true)
    private Category idCategory;

    @DatabaseField (columnName = "idInterview", foreignColumnName = "idInterview", canBeNull = false, foreign = true)
    private Interview idInterview;

    public Mark() {
    }

    @Override
    public String toString(){
        return "Mark{" +
                "idMark=" + idMark +
                ", value=" + value +
                ", idCategory=" + idCategory.getName() +
                ", idInterview=" + idInterview +
                '}';
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
