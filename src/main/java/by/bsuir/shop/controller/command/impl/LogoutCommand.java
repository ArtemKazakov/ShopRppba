package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.controller.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command that logouts user<br/>
 * Implements {@link Command}
 */
public class LogoutCommand implements Command{
    private static final String SRC="Controller?action=gotomain";

    private final static Logger logger = LogManager.getLogger(LogoutCommand.class.getName());

    /**
     * Invalidates current session
     * @param request incoming request.
     * @return link to main page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        request.getSession(false).invalidate();

        return SRC;
    }
}
