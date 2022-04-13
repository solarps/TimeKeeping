package com.my.timekeeping.commands.activity;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.DAO.DBManager;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RefuseActivityCommand implements Command {
    Logger logger = LogManager.getLogger(RefuseActivityCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("Command started");
        Long userId = Long.valueOf(req.getParameter("user_id"));
        Long activityId = Long.valueOf(req.getParameter("activity_id"));
        DBManager.getInstance().unfollowActivity(userId, activityId);
        return "controller?command=getAllUsers";
    }
}
