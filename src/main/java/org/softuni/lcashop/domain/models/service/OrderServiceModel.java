package org.softuni.lcashop.domain.models.service;

import org.modelmapper.ModelMapper;
import org.softuni.lcashop.domain.entities.Order;
import org.softuni.lcashop.mappings.IHaveCustomMappings;

import java.math.BigDecimal;

public class OrderServiceModel implements IHaveCustomMappings {

    private String imageUrl;
    private String name;
    private BigDecimal price;
    private String customer;

    public OrderServiceModel() {
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

    @Override
    public void configureMappings(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Order.class, OrderServiceModel.class)
                .addMapping(
                        entity -> entity.getProduct().getName(),
                        (dto, value) -> dto.setName((String) value)
                )
                .addMapping(
                        entity -> entity.getProduct().getPrice(),
                        (dto, value) -> dto.setPrice((BigDecimal) value)
                )
                .addMapping(
                        entity -> entity.getProduct().getImageUrl(),
                        (dto, value) -> dto.setImageUrl((String) value)
                )
                .addMapping(
                        entity -> entity.getUser().getUsername(),
                        (dto, value) -> dto.setCustomer((String) value)
                );
    }
}
