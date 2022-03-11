package com.my.timekeeping.Commands;

import com.my.timekeeping.DAO.DBManager;
import com.my.timekeeping.DAO.UserDAO;
import com.my.timekeeping.DTO.ActivityDTO;
import com.my.timekeeping.DTO.UserDTO;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetAllUsersCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GetAllUsersCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("Command start");
        List<UserDTO> userList = DBManager.getInstance().getAllUsersWithActivities();
        req.setAttribute("userList", userList);
        return "usersList.jsp";
    }
}
