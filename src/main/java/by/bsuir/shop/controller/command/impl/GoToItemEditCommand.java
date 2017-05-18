package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.service.ItemService;
import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.entity.ShopItem;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command that loads item's info for editing<br/>
 * Implements {@link Command}
 */
public class GoToItemEditCommand implements Command{
    public final static String SRC = "/WEB-INF/jsp/edititem.jsp";
    public final static String ATTR_ID = "item_id";
    public final static String ATTR_ITEM = "item";

    private final static Logger logger = LogManager.getLogger(GoToItemEditCommand.class.getName());

    /**
     * Loads item's info
     * @param request incoming request.
     * @return link to editing page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ServiceFactory factory = ServiceFactory.getInstance();
        ItemService itemService = factory.getItemService();

        try {
            ShopItem item = itemService.loadItem(request.getParameter(ATTR_ID));
            request.setAttribute(ATTR_ITEM, item);
        } catch (ServiceException ex){
            logger.error(ex);
            throw new CommandException(ex);
        }

        return SRC;
    }
}
