package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Collection;

/**
 * Created by momelnikov on 04.07.2016.
 */
@DatabaseTable
public class Category {
    @DatabaseField(columnName = "idCategory", generatedId = true)
    private int idCategory;

    @DatabaseField(columnName = "name",canBeNull = false)
    private String name;

    @ForeignCollectionField(foreignFieldName = "idCategory", eager = true)
    private Collection<Mark>  marks;

    public Category() {
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + idCategory + '\'' +
                "name='" + name + '\'' +
                '}';
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Collection<Mark> marks) {
        this.marks = marks;
    }

    public int getIdCategory() {

        return idCategory;
    }
}