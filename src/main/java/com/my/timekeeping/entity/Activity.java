package com.my.timekeeping.entity;

public class Activity {
    private Long id;
    private String name;
    private String category;
    private Boolean followed;

    public Activity(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public Activity(String name, String category, Boolean followed) {
        this.name = name;
        this.category = category;
        this.followed = followed;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
