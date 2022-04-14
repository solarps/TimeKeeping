package com.my.timekeeping.commands.user;

import com.my.timekeeping.commands.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class for logout user.
 * Class implements the Command interface {@link com.my.timekeeping.commands.Command} and overrides execute method.
 *
 * @author Andrey
 * @version 1.0
 */
public class LogoutCommand implements Command {

    Logger logger = LogManager.getLogger(LogoutCommand.class);

    /**
     * This method remove user from session
     *
     * @param req  http-Request in which we get session
     * @param resp http-Response
     * @return adress to controller {@link com.my.timekeeping.Controller}
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.trace("Command start");
        HttpSession session = req.getSession();
        if (session != null) {
            session.removeAttribute("user");
            //session.invalidate();
        }
        logger.trace("logout finished");
        return "index.jsp";
    }
}
