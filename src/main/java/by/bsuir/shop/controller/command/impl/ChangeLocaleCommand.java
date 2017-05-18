package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command for changing localization<br/>
 * Implements {@link Command}
 */
public class ChangeLocaleCommand implements Command {
    private static final String ATTR_LOCALE = "locale";
    public static final String LANG_ATTR = "lang";

    public final static String SRC = "Controller?action=gotomain";

    private final static Logger logger = LogManager.getLogger(ChangeLocaleCommand.class.getName());

    /**
     * Changes locale attribute of the current session
     * @param request incoming request.
     * @return link to main page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String locale = request.getParameter(LANG_ATTR);
        request.getSession(true).setAttribute(ATTR_LOCALE, locale);

        return SRC;
    }
}
