package model;

import entity.Category;

/**
 * Created by avpolyakov on 06.07.2016.
 */
public class CategoryRow {
    private Category categoryName;
    private Double mark;

    public CategoryRow() {
    }

    public Category getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(Category categoryName) {
        this.categoryName = categoryName;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }
}
