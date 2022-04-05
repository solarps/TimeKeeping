package com.my.timekeeping.builder;

import com.my.timekeeping.entity.Role;

public interface Builder {
    void setId(Long id);
    void setRole(Role role);
    void setName(String name);
    void setLogin(String login);
    void setPassword(String password);
}
