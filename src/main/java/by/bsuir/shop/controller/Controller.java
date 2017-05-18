package by.bsuir.shop.controller;

import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@MultipartConfig
public class Controller extends HttpServlet{
    private static final String PARAMETER_ACTION = "action";
    private static final String PARAMETER_ERROR = "error";
    private static final String SRC_ERR="/WEB-INF/jsp/error.jsp";

    private final ControllerHelper helper = new ControllerHelper();
    private final static Logger logger = LogManager.getLogger(Controller.class.getName());

    public Controller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String goTo = null;
        String commandName = null;
        Command command = null;

        try {
            commandName = request.getParameter(PARAMETER_ACTION);
            logger.info("proceeding GET: " + commandName);
            command = helper.getCommand(commandName);
            goTo = command.execute(request);
            RequestDispatcher dispatcher = request.getRequestDispatcher(goTo);

            dispatcher.forward(request, response);
        } catch (CommandException e) {
            logger.error(e);
            request.getRequestDispatcher(SRC_ERR).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String goTo = null;
        String commandName = null;
        Command command = null;

        try {
            commandName = request.getParameter(PARAMETER_ACTION);
            logger.info("proceeding POST: " + commandName);
            command = helper.getCommand(commandName);
            goTo = command.execute(request);
            response.sendRedirect(goTo);
        } catch (CommandException e) {
            logger.error(e);
            request.getRequestDispatcher(SRC_ERR).forward(request, response);
        }
    }
}