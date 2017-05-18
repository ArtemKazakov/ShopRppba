package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.entity.ShopItem;
import by.bsuir.shop.service.CartService;
import by.bsuir.shop.service.ItemService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.entity.Cart;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command that removes item from cart<br/>
 * Implements {@link Command}
 */
public class RemoveFromCartCommand implements Command{
    private final static Logger logger = LogManager.getLogger(EditItemCommand.class.getName());

    private final static String ATTR_ITEM_ID = "item_id";
    private final static String ATTR_USER_ID = "user_id";
    private final static String ATTR_CART = "cart";
    private final static String ATTR_ITEM = "item";
    private final static String ATTR_SIZE = "size";

    private final static String SRC = "Controller?action=redirect&page=cart.jsp";

    /**
     * Removes item from user's cart
     * @param request incoming request.
     * @return link to cart
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ServiceFactory factory = ServiceFactory.getInstance();
        CartService cartService = factory.getCartService();
        ItemService itemService = factory.getItemService();

        boolean status;

        String itemId = request.getParameter(ATTR_ITEM_ID);
        String userId = request.getParameter(ATTR_USER_ID);
        String size = request.getParameter(ATTR_SIZE);

        try {
            ShopItem item = itemService.loadItem(itemId);
            item.setSize(size);
            Cart cart = (Cart) request.getSession().getAttribute(ATTR_CART);
            cart.removeItem(item);
            status = cartService.removeItemFromCart(userId, itemId, size);
        } catch (ServiceException ex){
            logger.error(ex);
            throw new CommandException(ex);
        }

        return SRC;
    }
}
