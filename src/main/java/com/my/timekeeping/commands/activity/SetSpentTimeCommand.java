package com.my.timekeeping.commands.activity;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.dao.DBManager;
import com.my.timekeeping.dto.UserDTO;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class to set spent time for user activity in database.
 * Class implements the Command interface {@link Command} and overrides execute method.
 *
 * @author Andrey
 * @version 1.0
 */
public class SetSpentTimeCommand implements Command {
    Logger logger = LogManager.getLogger(SetSpentTimeCommand.class);

    /**
     * This method gets set spent time for activity(update database).
     *
     * @param req  http-Request in which we get needed parameters
     * @param resp http-Response
     * @return adress to controller {@link com.my.timekeeping.Controller}
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("Command started");
        String time = req.getParameter("hours") + ":" +
                req.getParameter("minutes") + ":" +
                req.getParameter("seconds");


        Long activity_id = Long.parseLong(req.getParameter("activity_id"));
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");

        DBManager.getInstance().setSpentTime(activity_id, userDTO.getId(), time);


        return "controller?command=getAllActivity";
    }
}
