package com.my.timekeeping.Commands;

import com.my.timekeeping.DAO.DBManager;
import com.my.timekeeping.DTO.ActivityDTO;
import com.my.timekeeping.entity.Activity;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

public class DeleteActivityCommand implements Command {
    Logger logger = LogManager.getLogger(DeleteActivityCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException, EncryptException {
        logger.trace("Command started");
        req.getSession().removeAttribute("error");
        final Long id = Long.valueOf(req.getParameter("id"));
        final String name = req.getParameter("name");
        final String category = req.getParameter("category");

        if (!DBManager.getInstance().isActivityExist(name, category)) {
            req.getSession().setAttribute("error", "Activity is not exist");
            return "controller?command=getAllActivity";
        }

        ActivityDTO activityDTO = new ActivityDTO(id, name, category);

        DBManager.getInstance().deleteActivity(activityDTO);

        if (DBManager.getInstance().countOfCategory(activityDTO.getCategory()) == 0) {
            DBManager.getInstance().deleteCategory(activityDTO.getCategory());
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
