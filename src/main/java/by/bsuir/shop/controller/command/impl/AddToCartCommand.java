package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.service.CartService;
import by.bsuir.shop.service.ItemService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.entity.Cart;
import by.bsuir.shop.entity.ShopItem;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command for adding items to cart<br/>
 * Implements {@link Command}
 */
public class AddToCartCommand implements Command{
    private final static Logger logger = LogManager.getLogger(AddToCartCommand.class.getName());

    private final static String ATTR_ITEM_ID = "item_id";
    private final static String ATTR_USER_ID = "user_id";
    private final static String ATTR_CART = "cart";
    private final static String ATTR_ITEM = "item";
    private final static String ATTR_SIZE = "size";

    private final static String COMMAND_TEMPLATE = "Controller?action=gotoitem&item_id=";

    /**
     * Collects parameters and sends them to {@link CartService}
     * @param request incoming request.
     * @return url of added item
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

        String src;

        try {
            ShopItem item = itemService.loadItem(itemId);
            item.setSize(size);
            Cart cart = (Cart) request.getSession().getAttribute(ATTR_CART);
            cart.addItem(item);
            status = cartService.addItemToCart(userId, itemId, size);
            src = COMMAND_TEMPLATE + itemId;
        } catch (ServiceException ex){
            logger.error(ex);
            throw new CommandException(ex);
        }

        return src;
    }
}
