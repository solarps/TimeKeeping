package com.my.timekeeping.Commands;

import com.my.timekeeping.DAO.DBManager;
import com.my.timekeeping.entity.Activity;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateActivityCommand implements Command {

    Logger logger = LogManager.getLogger(CreateActivityCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("command started");

        final String name = req.getParameter("name");
        final String category = req.getParameter("category");

        if (DBManager.getInstance().isActivityExist(name, category)) {
            req.setAttribute("error", "Activity is already exist");
            return "activityList.jsp";
        }

        Activity activity = new Activity(name, category);
        logger.trace("activity to register:{}", activity);
        DBManager.getInstance().addActivity(activity);
        return "controller?command=getAllActivity";
    }
}
