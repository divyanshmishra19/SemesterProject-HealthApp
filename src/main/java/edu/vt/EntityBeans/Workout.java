package edu.vt.EntityBeans;

import java.io.Serializable;

public class Workout implements Serializable {


    private Integer id;
    private Float burnRate;
    private String name;
    private String category;

    public Workout(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getBurnRate() {
        return burnRate;
    }

    public void setBurnRate(Float burnRate) {
        this.burnRate = burnRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Workout(Float burnRate, String name, String category) {
        this.burnRate = burnRate;
        this.name = name;
        this.category = category;
    }
}
