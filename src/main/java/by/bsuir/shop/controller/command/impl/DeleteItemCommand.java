package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.service.ItemService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command for item removal<br/>
 * Implements {@link Command}
 */
public class DeleteItemCommand implements Command{
    public final static String SRC = "Controller?action=gotomain";
    public final static String ATTR_ID = "item_id";

    private final static Logger logger = LogManager.getLogger(DeleteItemCommand.class.getName());

    /**
     * Removes item from catalog
     * @param request incoming request.
     * @return link to main page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ServiceFactory factory = ServiceFactory.getInstance();
        ItemService service = factory.getItemService();

        try{
            service.deleteItem(request.getParameter(ATTR_ID));
        } catch (ServiceException ex){
            logger.error(ex);
            throw new CommandException(ex);
        }
        return SRC;
    }
}
