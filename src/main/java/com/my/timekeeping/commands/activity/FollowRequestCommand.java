package com.my.timekeeping.commands.activity;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.dao.DBManager;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This class for sending follow request.
 * Class implements the Command interface {@link com.my.timekeeping.commands.Command} and overrides execute method.
 *
 * @author Andrey
 * @version 1.0
 */
public class FollowRequestCommand implements Command {
    Logger logger = LogManager.getLogger(FollowRequestCommand.class);

    /**
     * This method gets needed parameters and create follow request to database.
     *
     * @param req  http-Request in which we get needed parameters
     * @param resp http-Response
     * @return adress to controller {@link com.my.timekeeping.Controller}
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("command started");
        Long userId = Long.valueOf(req.getParameter("user_id"));
        Long activityId = Long.valueOf(req.getParameter("activity_id"));
        DBManager.getInstance().followRequest(userId, activityId);
        String referer = "error.jsp";
        try {
            referer = new URI(req.getHeader("referer")).getPath();
            referer = referer.concat("?command=getAllActivity");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return referer;
        //return "controller?command=getAllActivity";
    }
}
