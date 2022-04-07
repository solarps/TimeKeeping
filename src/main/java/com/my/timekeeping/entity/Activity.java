package com.my.timekeeping.entity;

public class Activity {
    private Long id;
    private String name;
    private String category;
    private State state;

    public Activity(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public Activity(String name, String category, State state) {
        this.name = name;
        this.category = category;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
