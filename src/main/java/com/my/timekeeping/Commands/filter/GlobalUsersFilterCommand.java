package com.my.timekeeping.Commands.filter;

import com.my.timekeeping.Commands.Command;
import com.my.timekeeping.DAO.DBManager;
import com.my.timekeeping.DTO.UserDTO;
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

public class GlobalUsersFilterCommand implements Command {
    Logger logger = LogManager.getLogger(GlobalUsersFilterCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("command start");
        List<UserDTO> users = DBManager.getInstance().getAllUsersWithActivities();
        String[] roles = req.getParameterValues("role");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String sortType = req.getParameter("sort");

        if (name.isEmpty() && login.isEmpty() && roles == null){
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


    private List<UserDTO> searchByLogin(String login, List<UserDTO> userDTOList) {
        return userDTOList.stream()
                .filter(userDTO -> userDTO.getLogin().equals(login))
                .collect(Collectors.toList());
    }

    private List<UserDTO> filterByRole(String[] roles, List<UserDTO> userDTOList) {
        return userDTOList.stream().filter(userDTO -> Arrays.stream(roles)
                        .anyMatch(role -> userDTO.getRole().toString().equals(role)))
                .collect(Collectors.toList());
    }

    private List<UserDTO> filterByName(String name, List<UserDTO> userDTOList) {
        return userDTOList.stream()
                .filter(userDTO -> userDTO.getName().contains(name))
                .collect(Collectors.toList());
    }
}