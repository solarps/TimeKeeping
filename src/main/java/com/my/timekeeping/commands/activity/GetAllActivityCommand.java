package com.my.timekeeping.commands.activity;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.DAO.ActivityDAO;
import com.my.timekeeping.DTO.ActivityDTO;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetAllActivityCommand implements Command {
    Logger logger = LogManager.getLogger(GetAllActivityCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("Command started");
        //UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        List<ActivityDTO> activityList = ActivityDAO.getInstance().getAllActivities();
        List<String> categories = ActivityDAO.getInstance().getAllCategories();
        req.setAttribute("categoryList", categories);
        req.setAttribute("activityList", activityList);
        return "activityList.jsp";
    }
}
