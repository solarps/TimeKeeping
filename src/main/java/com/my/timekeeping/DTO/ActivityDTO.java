package com.my.timekeeping.DTO;

import com.my.timekeeping.entity.State;

import java.io.Serializable;

public class ActivityDTO implements Serializable {
    private Long id;
    private String name;
    private String category;
    private State state;

    public ActivityDTO() {
    }

    public ActivityDTO(Long id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
