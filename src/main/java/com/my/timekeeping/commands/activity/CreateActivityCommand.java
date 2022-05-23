package com.my.timekeeping.commands.activity;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.dao.ActivityDAO;
import com.my.timekeeping.dao.DBManager;
import com.my.timekeeping.entity.Activity;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class for create new activity.
 * Class implements the Command interface {@link Command} and overrides execute method.
 *
 * @author Andrey
 * @version 1.0
 */

public class CreateActivityCommand implements Command {

    Logger logger = LogManager.getLogger(CreateActivityCommand.class);

    /**
     * This method for create new activity. Method checks is activity with equal parameters exists in database.
     *
     * @param req  http-Request in which we get name and activity category
     * @param resp http-Response
     * @return adress to controller {@link com.my.timekeeping.Controller}
     */
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

        Activity activity = Activity.newBuilder().setName(name).setCategory(category).build();
        logger.trace("activity to register:{}", activity);
        DBManager.getInstance().addActivity(activity);
        return "controller?command=getAllActivity";
    }
}
