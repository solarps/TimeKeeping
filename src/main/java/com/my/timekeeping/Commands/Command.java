package com.my.timekeeping.Commands;

import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException;
}
