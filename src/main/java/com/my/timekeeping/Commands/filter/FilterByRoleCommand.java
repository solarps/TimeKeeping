package com.my.timekeeping.Commands.filter;

import com.my.timekeeping.Commands.Command;
import com.my.timekeeping.DAO.UserDAO;
import com.my.timekeeping.DTO.UserDTO;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class FilterByRoleCommand implements Command {

    private static final Logger logger = LogManager.getLogger(FilterByRoleCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("command start");

        int[] roles_id = Arrays.stream(req.getParameterValues("role")).mapToInt(Integer::parseInt).toArray();

        List<UserDTO> userList = UserDAO.getInstance().getAllUsersByRole(roles_id);
        req.setAttribute("userList", userList);

        return "usersList.jsp";
    }
}
