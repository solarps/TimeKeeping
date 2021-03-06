package com.my.timekeeping.commands.filter;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.dao.ActivityDAO;
import com.my.timekeeping.dao.UserDAO;
import com.my.timekeeping.dto.ActivityDTO;
import com.my.timekeeping.dto.UserDTO;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class for filtering activity in activityList.jsp
 * Class implements the Command interface {@link com.my.timekeeping.commands.Command} and overrides execute method
 *
 * @author Andrey
 * @version 1.0
 */

public class GlobalActivityFilterCommand implements Command {
    Logger logger = LogManager.getLogger(GlobalActivityFilterCommand.class);

    /**
     * This method gets parameters for filtering from httpRequest. Gets activity list and category list and filtering its
     *
     * @param req  http-Reques in which we take parameters for filtering
     * @param resp http-Response
     * @return adress to controller {@link com.my.timekeeping.Controller}
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("command started");
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        userDTO = UserDAO.getInstance().getUserByLogin(userDTO.getLogin());
        List<ActivityDTO> activityList = ActivityDAO.getInstance().getAllActivities(userDTO.getId());
        List<String> categories = ActivityDAO.getInstance().getAllCategories();
        String[] states = req.getParameterValues("state");
        String name = req.getParameter("name");
        String category = req.getParameter("category");

        if (name.isEmpty() && category.equals("ALL") && states == null) {
            return "controller?command=getAllActivity";
        }
        if (states.length != 0) {
            logger.trace("search by state started");
            activityList = searchByState(activityList, states);
            logger.trace("searched by state");
        }
        if (!name.isEmpty()) {
            logger.trace("search by name started");
            activityList = searchByName(activityList, name);
            logger.trace("searched by name");
        }
        if (!category.equals("ALL")) {
            logger.trace("search by category started");
            activityList = searchByCategory(activityList, category);
            logger.trace("searched by category");
        }

        req.setAttribute("categoryList", categories);
        req.setAttribute("activityList", activityList);
        return "activityList.jsp";
    }


    /**
     * This method for search activity by state
     *
     * @param activityDTOS list of activities
     * @param states       needed states
     */
    private List<ActivityDTO> searchByState(List<ActivityDTO> activityDTOS, String[] states) {
        return activityDTOS.stream()
                .filter(activityDTO -> Arrays.stream(states)
                        .anyMatch(state -> activityDTO.getState().toString().equals(state)))
                .collect(Collectors.toList());
    }

    /**
     * This method for search activity by category
     *
     * @param activityDTOS list of activities
     * @param category     name of needed category
     */
    private List<ActivityDTO> searchByCategory(List<ActivityDTO> activityDTOS, String category) {
        return activityDTOS.stream()
                .filter(activityDTO -> activityDTO.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    /**
     * This method for search activity by name
     *
     * @param activityDTOS list of activities
     * @param name         name of needed activity
     */
    private List<ActivityDTO> searchByName(List<ActivityDTO> activityDTOS, String name) {
        return activityDTOS.stream()
                .filter(activityDTO -> activityDTO.getName().equals(name))
                .collect(Collectors.toList());
    }
}
