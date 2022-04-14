package com.my.timekeeping.commands.activity;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.dao.DBManager;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class to unfollow activity in database.
 * Class implements the Command interface {@link com.my.timekeeping.commands.Command} and overrides execute method.
 *
 * @author Andrey
 * @version 1.0
 */
public class UnfollowActivityCommand implements Command {
    Logger logger = LogManager.getLogger(UnfollowActivityCommand.class);

    /**
     * This method gets user id and activity id to unfollow activity (update database).
     *
     * @param req  http-Request in which we get needed parameters
     * @param resp http-Response
     * @return adress to controller {@link com.my.timekeeping.Controller}
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("Command started");
        Long userId = Long.valueOf(req.getParameter("user_id"));
        Long activityId = Long.valueOf(req.getParameter("activity_id"));
        DBManager.getInstance().unfollowActivity(userId, activityId);
        return "controller?command=getAllActivity";
    }
}
