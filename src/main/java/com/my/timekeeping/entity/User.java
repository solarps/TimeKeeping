package com.my.timekeeping.entity;

import java.io.Serializable;

public class User implements Serializable {

    private Long id;
    private Role role;
    private String name;
    private String login;
    private String password;

    public User(Long id, Role role, String name, String login, String password) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User(Long id, String name, String login, String password) {
        this.id = id;
        this.role = Role.USER;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User(String name, String login, String password) {
        this.role = Role.USER;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }


    public String getPassword() {
        return password;
    }

}
