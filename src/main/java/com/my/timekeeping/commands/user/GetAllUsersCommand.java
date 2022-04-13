package com.my.timekeeping.commands.user;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.DAO.DBManager;
import com.my.timekeeping.DTO.UserDTO;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * This class for output all users on usersList.jsp
 * Class implements the Command interface {@link com.my.timekeeping.commands.Command} and overrides execute method
 *
 * @author Andrey
 * @version 1.0
 */

public class GetAllUsersCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GetAllUsersCommand.class);

    /**
     * This method for outputs all users
     *
     * @param req  httpRequest in which we set list of users
     * @param resp httpResponse
     * @return adress to controller {@link com.my.timekeeping.Controller}
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("Command start");
        List<UserDTO> userList = DBManager.getInstance().getAllUsersWithActivities();
        req.setAttribute("userList", userList);
        return "usersList.jsp";
    }
}
