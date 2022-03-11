package com.my.timekeeping.Commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    Logger logger = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        logger.trace("Command start");
        HttpSession session = req.getSession();
        if (session != null) {
            session.removeAttribute("user");
            session.invalidate();
        }
        logger.trace("Command finished");
        return "index.jsp";
    }
}
