package com.my.timekeeping.commands.filter;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.dao.DBManager;
import com.my.timekeeping.dto.UserDTO;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class for filtering users in usersList.jsp.
 * Class implements the Command interface {@link com.my.timekeeping.commands.Command} and overrides execute method.
 *
 * @author Andrey
 * @version 1.0
 */


public class GlobalUsersFilterCommand implements Command {
    Logger logger = LogManager.getLogger(GlobalUsersFilterCommand.class);

    /**
     * This method gets parameters for filtering from httpRequest. Gets list of users with their activities.
     *
     * @param req  http-Reques in which we take parameters for filtering
     * @param resp http-Response
     * @return adress to controller {@link com.my.timekeeping.Controller}
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("command start");
        List<UserDTO> users = DBManager.getInstance().getAllUsersWithActivities();
        String[] roles = req.getParameterValues("role");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String sortType = req.getParameter("sort");

        if (name.isEmpty() && login.isEmpty() && roles == null) {
            return "controller?command=getAllUsers";
        }

        if (!login.isEmpty()) {
            logger.trace("search user by login started");
            users = searchByLogin(login, users);
            logger.trace("searched user by login");
        }
        if (!name.isEmpty()) {
            logger.trace("filter users by name started");
            users = filterByName(name, users);
            logger.trace("filtered users by name");
        }
        if (roles != null) {
            logger.trace("filter users by role started");
            users = filterByRole(roles, users);
            logger.trace("filtered users by role");
        }

        sortByType(sortType, users);

        req.setAttribute("userList", users);
        return "usersList.jsp";
    }


    /**
     * This method for sort users.
     *
     * @param sortType type of sorting
     * @param users    list of users
     */
    private void sortByType(String sortType, List<UserDTO> users) {
        switch (sortType) {
            case "ROLE":
                users.sort(Comparator.comparing(userDTO -> userDTO.getRole().toString()));
                break;
            case "NAME":
                users.sort(Comparator.comparing(UserDTO::getName));
                break;
            case "LOGIN":
                users.sort(Comparator.comparing(UserDTO::getLogin));
                break;
        }
    }

    /**
     * This method for search user by login
     *
     * @param login    login of user
     * @param userDTOS list of users
     */
    private List<UserDTO> searchByLogin(String login, List<UserDTO> userDTOS) {
        return userDTOS.stream()
                .filter(userDTO -> userDTO.getLogin().equals(login))
                .collect(Collectors.toList());
    }

    //TODO check necessity of array

    /**
     * This method for filter users by roles
     *
     * @param roles    array of roles of users
     * @param userDTOS list of users
     */
    private List<UserDTO> filterByRole(String[] roles, List<UserDTO> userDTOS) {
        return userDTOS.stream().filter(userDTO -> Arrays.stream(roles)
                        .anyMatch(role -> userDTO.getRole().toString().equals(role)))
                .collect(Collectors.toList());
    }

    /**
     * This method for filter users by their name
     *
     * @param name     name of user
     * @param userDTOS list of users
     */
    private List<UserDTO> filterByName(String name, List<UserDTO> userDTOS) {
        return userDTOS.stream()
                .filter(userDTO -> userDTO.getName().contains(name))
                .collect(Collectors.toList());
    }
}