package com.my.timekeeping.DTO;

import com.my.timekeeping.entity.Role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Serializable {
    private int id;
    private Role role;
    private String login;
    private String name;
    private String password;
    private final List<ActivityDTO> activities = new ArrayList<>();

    public List<ActivityDTO> getActivities() {
        return activities;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}