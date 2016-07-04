package entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.sun.istack.internal.NotNull;

/**
 * Created by momelnikov on 04.07.2016.
 */
@DatabaseTable
public class Category {
    @DatabaseField(generatedId = true)
    private int idCategory;

    @DatabaseField(canBeNull = false)
    private String Name;

    @ForeignCollectionField
    private ForeignCollection<Mark>  Marks;

    public Category() {
    }

    public int getIdCategory() {
        return idCategory;
    }

    public String getName() {
        return Name;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public void setName(String name) {
        Name = name;
    }

    public ForeignCollection<Mark> getMarks() {
        return Marks;
    }

    public void setMarks(ForeignCollection<Mark> marks) {
        Marks = marks;
    }
}