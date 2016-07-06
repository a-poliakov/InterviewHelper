package model;

import entity.Category;

/**
 * Created by avpolyakov on 06.07.2016.
 */
public class CategoryRow {
    private Category category;
    private Double value;

    public CategoryRow(Category category, Double value) {
        this.category = category;
        this.value = value;
    }

    @Override
    public String toString() {
        return "CategoryRow{" +
                "category=" + category +
                ", value=" + value +
                '}';
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
