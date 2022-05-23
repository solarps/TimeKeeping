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
 * This class for confirm user follow request by admin.
 * Class implements the Command interface {@link Command} and overrides execute method.
 *
 * @author Andrey
 * @version 1.0
 */
public class ConfirmRequestCommand implements Command {

    Logger logger = LogManager.getLogger(ConfirmRequestCommand.class);

    /**
     * This method for confirms follow request and update database.
     *
     * @param req  http-Request in which we get user id and activity id, which use for update database
     * @param resp http-Response
     * @return adress to controller {@link com.my.timekeeping.Controller}
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("Command started");
        Long userId = Long.valueOf(req.getParameter("user_id"));
        Long activityId = Long.valueOf(req.getParameter("activity_id"));
        DBManager.getInstance().confirmFollowRequest(userId, activityId);
        return "controller?command=getAllUsers";
    }
}
