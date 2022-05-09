package com.my.timekeeping;

import com.my.timekeeping.commands.Command;
import com.my.timekeeping.commands.CommandContainer;
import com.my.timekeeping.exceptions.DAOException;
import com.my.timekeeping.exceptions.EncryptException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class controller for realizatoin MVC-pattern.
 * It receives requests from the View layer and processes them.
 * The requests are further sent to Model layer (Command pattern)
 * for data processing, and once they are processed,
 * the data is sent back to the Controller and then displayed on the View.
 *
 * @author Andrey
 * @version 1.0
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {
    Logger logger = LogManager.getLogger(Controller.class);

    /**
     * This method handles requests to get
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = processRequest(req, resp);
        req.getRequestDispatcher(address).forward(req, resp);
    }
    /**
     * This method handles post requests
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String address = processRequest(req, resp);
        resp.sendRedirect(address);
    }

    /**
     * This method for request processing and executing commands which get from client
     *
     * @return jsp address
     */
    private String processRequest(HttpServletRequest req, HttpServletResponse resp) {
        String commandName = req.getParameter("command");
        logger.trace("Request parameter command- -> {}", commandName);
        Command command = CommandContainer.getCommand(commandName);
        String address = "error.jsp";
        try {
            address = command.execute(req, resp);
        } catch (DAOException | EncryptException e) {
            logger.error(e.getMessage());
            return "error.jsp";
        }
        return address;
    }
}
