package com.my.timekeeping.commands.activity;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.dao.ActivityDAO;
import com.my.timekeeping.dto.ActivityDTO;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This class for delete activity from database.
 * Class implements the Command interface {@link com.my.timekeeping.commands.Command} and overrides execute method.
 *
 * @author Andrey
 * @version 1.0
 */
public class DeleteActivityCommand implements Command {
    Logger logger = LogManager.getLogger(DeleteActivityCommand.class);

    /**
     * This method for delete activity. Method checks is activity with equal parameters exists in database.
     * If count of activity category equals 0 after delete activity, method delete category from database.
     *
     * @param req  http-Request in which we get needed parameters
     * @param resp http-Response
     * @return adress to controller {@link com.my.timekeeping.Controller}
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("Command started");
        req.getSession().removeAttribute("error");
        final Long id = Long.valueOf(req.getParameter("id"));
        final String name = req.getParameter("name");
        final String category = req.getParameter("category");

        if (!ActivityDAO.getInstance().isActivityExist(name, category)) {
            req.getSession().setAttribute("error", "Activity is not exist");
            return "controller?command=getAllActivity";
        }

        ActivityDTO activityDTO = new ActivityDTO(id, name, category);

        ActivityDAO.getInstance().deleteActivity(activityDTO);

        if (ActivityDAO.getInstance().countOfCategory(category) == 0) {
            ActivityDAO.getInstance().deleteCategory(category);
        }

        String referer = "error.jsp";
        try {
            referer = new URI(req.getHeader("referer")).getPath();
            referer = referer.concat("?command=getAllActivity");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return referer;
        //return "controller?command=getAllActivity";
    }
}
