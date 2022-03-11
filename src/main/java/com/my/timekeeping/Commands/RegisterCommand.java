package com.my.timekeeping.Commands;

import com.my.timekeeping.DAO.DBManager;
import com.my.timekeeping.entity.User;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import com.my.timekeeping.DAO.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        req.getSession().removeAttribute("error");
        logger.trace("command started");
        final String name = req.getParameter("name");
        final String login = req.getParameter("login");
        //final String email = req.getParameter("email");
        final String password = req.getParameter("password");
        //final int roleId = Integer.parseInt(req.getParameter("role"));
        if (DBManager.getInstance().isUserExist(login)) {
            req.getSession().setAttribute("error", "User with this login is already registered");
            return "register.jsp";
        }
        User user = new User(name, login, password);
        logger.debug("user to register: {}", user);
        DBManager.getInstance().addUser(user);
        req.getSession().setAttribute("user", user);
        logger.trace("user {} authorized", user);
        return "main.jsp";
    }
}
