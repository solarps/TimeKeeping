package com.my.timekeeping.Commands;

import com.my.timekeeping.DTO.ActivityDTO;
import com.my.timekeeping.DTO.UserDTO;
import com.my.timekeeping.PasswordUtil;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import com.my.timekeeping.DAO.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("Command started");
        req.getSession().removeAttribute("error");
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        UserDTO user = UserDAO.getInstance().getUserByLogin(login);
        if (user.getId() == -1 || !PasswordUtil.check(password, user.getPassword())) {
            req.getSession().setAttribute("error", "Unknown login or password try again.");
            return "login.jsp";
        }
        user.setPassword(null);
        req.getSession().setAttribute("user", user);
        logger.trace("user {} authorized", user);

        return "main.jsp";
    }
}
