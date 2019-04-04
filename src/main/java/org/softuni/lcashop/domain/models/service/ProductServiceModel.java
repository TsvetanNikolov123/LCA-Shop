package org.softuni.lcashop.domain.models.service;

import org.softuni.lcashop.domain.entities.Category;

import java.math.BigDecimal;
import java.util.List;

public class ProductServiceModel extends BaseServiceModel {

    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private List<CategoryServiceModel> categories;

    public ProductServiceModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<CategoryServiceModel> getCategories() {
        return this.categories;
    }

    public void setCategories(List<CategoryServiceModel> categories) {
        this.categories = categories;
    }
}
