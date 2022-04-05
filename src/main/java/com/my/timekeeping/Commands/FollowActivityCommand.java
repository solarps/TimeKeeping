package com.my.timekeeping.Commands;

import com.my.timekeeping.DAO.DBManager;
import com.my.timekeeping.DTO.UserDTO;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FollowActivityCommand implements Command {
    Logger logger = LogManager.getLogger(FollowActivityCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("Command started");
        Long user_id = Long.valueOf(req.getParameter("user_id"));
        Long activity_id = Long.valueOf(req.getParameter("activity_id"));
        DBManager.getInstance().followActivity(user_id, activity_id);
        return "controller?command=getAllActivity";
    }
}
