package com.my.timekeeping.Commands.filter;

import com.my.timekeeping.Commands.Command;
import com.my.timekeeping.DAO.DBManager;
import com.my.timekeeping.DTO.ActivityDTO;
import com.my.timekeeping.DTO.UserDTO;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;


public class GlobalActivityFilterCommand implements Command {
    Logger logger = LogManager.getLogger(GlobalActivityFilterCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("command started");
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        List<ActivityDTO> activityList = DBManager.getInstance().getAllActivities(user);
        List<String> categories = DBManager.getInstance().getAllCategories();
        String name = req.getParameter("name");
        String category = req.getParameter("category");

        if (name.isEmpty() && category.equals("ALL")){
            return "controller?command=getAllActivity";
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

    private List<ActivityDTO> searchByCategory(List<ActivityDTO> activityList, String category) {
        return activityList.stream()
                .filter(activityDTO -> activityDTO.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    private List<ActivityDTO> searchByName(List<ActivityDTO> activityDTOList, String name) {
        return activityDTOList.stream()
                .filter(activityDTO -> activityDTO.getName().equals(name))
                .collect(Collectors.toList());
    }
}
