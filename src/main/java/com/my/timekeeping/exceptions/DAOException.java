package com.my.timekeeping.exceptions;

public class DAOException extends Exception {
    private static final String DEFAULT_MESSAGE = "Dao exception.";

    public DAOException() {
    }

    public DAOException(String message) {
        super(message == null ? DEFAULT_MESSAGE : DEFAULT_MESSAGE + message);
    }
}
