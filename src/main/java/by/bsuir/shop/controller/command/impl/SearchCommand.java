package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.entity.ShopItem;
import by.bsuir.shop.service.ItemService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Command that searchs items<br/>
 * Implements {@link Command}
 */
public class SearchCommand implements Command{
    private static final String SRC = "/WEB-INF/jsp/search.jsp";

    private final static Logger logger = LogManager.getLogger(SearchCommand.class.getName());

    private final static String QUERRY_ATTR = "querry";
    private final static String ITEMS_ATTR = "items";
    private final static String ITEMSIZE_ATTR = "itemsSize";

    /**
     * Load requested items
     * @param request incoming request.
     * @return link to search page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ItemService itemService = serviceFactory.getItemService();

        String querry = request.getParameter(QUERRY_ATTR);
        List<ShopItem> items = new ArrayList<>();
        try{
            items = itemService.searchItems(querry);
        } catch (ServiceException ex){

        }

        request.setAttribute(QUERRY_ATTR, querry);
        request.setAttribute(ITEMS_ATTR, items);
        if(items != null){
            request.setAttribute(ITEMSIZE_ATTR, items.size());
        } else {
            request.setAttribute(ITEMSIZE_ATTR, 0);
        }


        return SRC;
    }
}
