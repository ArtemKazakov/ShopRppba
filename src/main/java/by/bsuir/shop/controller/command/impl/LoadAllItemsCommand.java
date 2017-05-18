package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.entity.ShopItem;
import by.bsuir.shop.entity.Subscription;
import by.bsuir.shop.entity.User;
import by.bsuir.shop.service.ItemService;
import by.bsuir.shop.service.SubscriptionService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Command that loads all items of the requested category<br/>
 * Implements {@link Command}
 */
public class LoadAllItemsCommand implements Command {
    private static final String SRC="/WEB-INF/jsp/category.jsp";
    private static final String ATTR_ITEMS = "items";
    private static final String ATTR_CATEGORY = "category";
    private final static String USER_ATTR = "user";


    private final static Logger logger = LogManager.getLogger(LoadAllItemsCommand.class.getName());

    /**
     * loads items of the requested category
     * @param request incoming request.
     * @return link to category page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            ServiceFactory factory = ServiceFactory.getInstance();
            ItemService service = factory.getItemService();
            SubscriptionService subscriptionService = factory.getSubscriptionService();
            String category = request.getParameter(ATTR_CATEGORY);
            List<ShopItem> items = service.getAllItems(category);
            User user = (User) request.getSession(false).getAttribute(USER_ATTR);
            if (user != null) {
                List<Subscription> subscriptions = subscriptionService.getByUser(user.getId());
                for (Subscription subscription : subscriptions){
                    if (subscription.getCategory().equals(category) && subscription.getUser().getId().equals(user.getId())){
                        request.setAttribute("sub", true);
                    }
                }
            }
            request.setAttribute(ATTR_ITEMS, items);
            request.setAttribute(ATTR_CATEGORY, category);
        } catch (ServiceException ex){
            logger.error(ex);
            throw new CommandException(ex);
        }

        return SRC;
    }
}
