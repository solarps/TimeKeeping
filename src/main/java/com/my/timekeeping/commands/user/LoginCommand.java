package com.my.timekeeping.commands.user;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.dto.UserDTO;
import com.my.timekeeping.PasswordUtil;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import com.my.timekeeping.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * This class for login user.
 * Class implements the Command interface {@link com.my.timekeeping.commands.Command} and overrides execute method.
 *
 * @author Andrey
 * @version 1.0
 */
public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    /**
     * This method for login user. Method checks is password correct and set user in httpRequest session
     *
     * @param req  http-Request in which get login and password, and set user which we get from database
     * @param resp http-Response
     * @return adress to controller {@link com.my.timekeeping.Controller}
     */
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

        return "index.jsp";
    }
}
