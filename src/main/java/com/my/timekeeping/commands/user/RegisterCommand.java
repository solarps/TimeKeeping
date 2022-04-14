package com.my.timekeeping.commands.user;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.dao.UserDAO;
import com.my.timekeeping.entity.Role;
import com.my.timekeeping.entity.User;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class for register user.
 * Class implements the Command interface {@link com.my.timekeeping.commands.Command} and overrides execute method.
 *
 * @author Andrey
 * @version 1.0
 */
public class RegisterCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);

    /**
     * This method for register user in database. Method check if user exists and register him
     * Class implements the Command interface {@link com.my.timekeeping.commands.Command} and overrides execute method.
     *
     * @param req  http-Request in which get parameters to registrate user
     * @param resp http-Response
     * @return adress to controller {@link com.my.timekeeping.Controller}
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        req.getSession().removeAttribute("error");
        logger.trace("command started");

        final String name = req.getParameter("name");
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        if (UserDAO.getInstance().isUserExist(login)) {
            req.getSession().setAttribute("error", "User with this login is already registered");
            return "register.jsp";
        }

        User user = User.newBuilder().setName(name).setLogin(login).setPassword(password).setRole(Role.USER).build();
        logger.debug("user to register: {}", user);
        user.setId(UserDAO.getInstance().addUser(user));
        user.setPassword(null);
        req.getSession().setAttribute("user", user);
        logger.trace("user {} authorized", user);
        return "index.jsp";
    }
}
