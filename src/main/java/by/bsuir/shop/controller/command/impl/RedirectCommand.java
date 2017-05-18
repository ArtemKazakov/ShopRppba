package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Command that redirects to requested page<br/>
 * Implements {@link Command}
 */
public class RedirectCommand implements Command{
    public final static String JSP_DIR = "WEB-INF/jsp/";
    public final static String ATTR_PAGE = "page";

    /**
     * Redirects to requested page
     * @param request incoming request.
     * @return link to requested page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String src = JSP_DIR + request.getParameter(ATTR_PAGE);

        return src;
    }
}
