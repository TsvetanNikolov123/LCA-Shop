package org.softuni.lcashop.domain.models.view;

import java.math.BigDecimal;

public class OrderViewModel {

    private String imageUrl;
    private String name;
    private BigDecimal price;
    private String customer;

    public OrderViewModel() {
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCustomer() {
        return this.customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
