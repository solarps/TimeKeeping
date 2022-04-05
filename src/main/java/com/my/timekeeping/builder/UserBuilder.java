package com.my.timekeeping.builder;

import com.my.timekeeping.entity.Role;
import com.my.timekeeping.entity.User;

public class UserBuilder implements Builder {
    private Long id;
    private Role role;
    private String name;
    private String login;
    private String password;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public User getResult(){
        return new User(id,role,name,login,password);
    }
}
