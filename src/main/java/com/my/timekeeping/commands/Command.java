package com.my.timekeeping.commands;

import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This interface for realization Command pattern
 *
 * @author Andrey
 * @version 1.0
 */

public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException;
}
