package com.my.timekeeping.entity;

import java.io.Serializable;

/**
 * This enum class for user role
 *
 * @author Andrey
 * @version 1.0
 */
public enum Role implements Serializable {
    ADMIN(1), USER(2);
    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
