package org.softuni.lcashop.domain.models.service;

public abstract class BaseServiceModel {

    private String id;

    protected BaseServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
