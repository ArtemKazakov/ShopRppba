package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * Command that (obviously) takes you to main page<br/>
 * Implements {@link Command}
 */
public class GoToMainCommand implements Command{
    public final static String SRC = "index.jsp";

    /**
     * Returns link to main
     * @param request incoming request.
     * @return link to index.jsp
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return SRC;
    }
}
