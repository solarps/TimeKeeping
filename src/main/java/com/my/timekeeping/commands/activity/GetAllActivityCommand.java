package com.my.timekeeping.commands.activity;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.dao.ActivityDAO;
import com.my.timekeeping.dao.UserDAO;
import com.my.timekeeping.dto.ActivityDTO;
import com.my.timekeeping.dto.UserDTO;
import com.my.timekeeping.entity.User;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * This class get all activity.
 * Class implements the Command interface {@link Command} and overrides execute method.
 *
 * @author Andrey
 * @version 1.0
 */
public class GetAllActivityCommand implements Command {
    Logger logger = LogManager.getLogger(GetAllActivityCommand.class);

    /**
     * This method for outputs all activity with their categories .
     *
     * @param req  http-Request in which we set list of activities
     * @param resp http-Response
     * @return adress to controller {@link com.my.timekeeping.Controller}
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("Command started");
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        List<ActivityDTO> activityList = ActivityDAO.getInstance().getAllActivities(userDTO.getId());
        List<String> categories = ActivityDAO.getInstance().getAllCategories();
        req.setAttribute("categoryList", categories);
        req.setAttribute("activityList", activityList);
        return "activityList.jsp";
    }
}
