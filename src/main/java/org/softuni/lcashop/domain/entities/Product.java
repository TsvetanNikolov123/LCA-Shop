package org.softuni.lcashop.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private List<Category> categories;

    public Product() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "image_url")
    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @ManyToMany(targetEntity = Category.class)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(
                    name = "product_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id", referencedColumnName = "id"
            )
    )
    public List<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}