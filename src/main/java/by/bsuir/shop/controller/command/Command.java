package by.bsuir.shop.controller.command;

import by.bsuir.shop.controller.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface {@code Command} provides behaviour for commands
 */
public interface Command {
    /**
     * <p>
     * @param request incoming request.
     * @return {@code String} url of nesscecary page.
     */
    String execute(HttpServletRequest request) throws CommandException;
}
