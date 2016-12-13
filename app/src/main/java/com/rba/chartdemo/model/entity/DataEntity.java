package com.rba.chartdemo.model.entity;

/**
 * Created by ricardobravo on 13/12/16.
 */

public class DataEntity {

    int id;
    String description;

    public DataEntity(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
