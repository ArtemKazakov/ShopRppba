package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.service.OrderService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command to change order status<br/>
 * Implements {@link Command}
 */
public class ChangeOrderStatusCommand implements Command{
    private final static Logger logger = LogManager.getLogger(ChangeOrderStatusCommand.class.getName());

    public final static String SRC_TEMPLATE = "Controller?action=gotoorder&order_id=";

    private final static String STATUS_ATTR = "status";
    private final static String ORDER_ID_ATTR = "order_id";

    /**
     * Changes order status
     * @param request incoming request.
     * @return link to order page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ServiceFactory factory = ServiceFactory.getInstance();
        OrderService orderService = factory.getOrderService();

        String orderStatus = request.getParameter(STATUS_ATTR);
        String orderId = request.getParameter(ORDER_ID_ATTR);

        String src = SRC_TEMPLATE + orderId;

        boolean status;
        try {
            status = orderService.changeOrderStatus(orderStatus, orderId);
        } catch (ServiceException ex){
            logger.error(ex);
            throw new CommandException(ex);
        }

        return src;
    }
}
