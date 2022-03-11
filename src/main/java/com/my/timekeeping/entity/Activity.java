package com.my.timekeeping.entity;

public class Activity {
    private int id;
    private String name;
    private String category;

    public Activity(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
