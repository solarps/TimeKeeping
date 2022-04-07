package com.my.timekeeping.Commands;

import com.my.timekeeping.DAO.DBManager;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

public class FollowRequestCommand implements Command {
    Logger logger = LogManager.getLogger(FollowRequestCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("command started");
        Long user_id = Long.valueOf(req.getParameter("user_id"));
        Long activity_id = Long.valueOf(req.getParameter("activity_id"));
        DBManager.getInstance().followRequest(user_id, activity_id);
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
