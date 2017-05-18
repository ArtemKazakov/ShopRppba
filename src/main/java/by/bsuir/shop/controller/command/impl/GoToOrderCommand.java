package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.entity.User;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.entity.Order;
import by.bsuir.shop.service.OrderService;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command that loads order info<br/>
 * Implements {@link Command}
 */
public class GoToOrderCommand implements Command{
    private final static Logger logger = LogManager.getLogger(GoToOrderCommand.class.getName());

    private final static String src = "/WEB-INF/jsp/order.jsp";

    private final static String USER_ATTR = "user";
    private final static String ORDER_ID_ATTR = "order_id";
    private final static String ORDER_ATTR = "order";
    private final static String ADMIN_ROLE_ATTR = "admin";
    private final static String ORDERS_SIZE_ATTR = "orders_size";

    /**
     * Loads order info from database
     * @param request incoming request.
     * @return link to order page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ServiceFactory factory = ServiceFactory.getInstance();
        OrderService orderService = factory.getOrderService();

        Order order;
        User user;
        try {
            order = orderService.getOrder(request.getParameter(ORDER_ID_ATTR));
            user = (User) request.getSession().getAttribute(USER_ATTR);

            if((!user.getRole().equals(ADMIN_ROLE_ATTR))&&(user.getId() != order.getCustomer().getId())){
                return null;
            }

            request.setAttribute(ORDER_ATTR, order);
        } catch (ServiceException ex){
            logger.error(ex);
            throw new CommandException(ex);
        }

        return src;
    }
}
