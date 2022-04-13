package com.my.timekeeping.commands.activity;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.DAO.ActivityDAO;
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
        req.getSession().removeAttribute("error");
        final String name = req.getParameter("name");
        final String category = req.getParameter("category");

        if (ActivityDAO.getInstance().isActivityExist(name, category)) {
            req.getSession().setAttribute("error", "Activity is already exist");
            logger.error("Activity is already exist");
            return "controller?command=getAllActivity";
        }

        //Activity activity = new ActivityBuilder().setName(name).setCategory(category).build();
        Activity activity = Activity.newBuilder().setName(name).setCategory(category).build();
        logger.trace("activity to register:{}", activity);
        DBManager.getInstance().addActivity(activity);
        return "controller?command=getAllActivity";
    }
}
