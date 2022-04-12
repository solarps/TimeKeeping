package com.my.timekeeping.entity;

import java.io.Serializable;

/**
 * This entity class for user with Builder class for builder pattern
 *
 * @author Andrey
 * @version 1.0
 */
public class User implements Serializable {

    private Long id;
    private Role role;
    private String name;
    private String login;
    private String password;

    private User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public static User.Builder newBuilder() {
        return new User().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public User.Builder setId(Long id) {
            User.this.id = id;
            return this;
        }

        public User.Builder setRole(Role role) {
            User.this.role = role;
            return this;
        }

        public User.Builder setName(String name) {
            User.this.name = name;
            return this;
        }

        public User.Builder setLogin(String login) {
            User.this.login = login;
            return this;
        }

        public User.Builder setPassword(String password) {
            User.this.password = password;
            return this;
        }

        public User build() {
            return User.this;
        }
    }
}
