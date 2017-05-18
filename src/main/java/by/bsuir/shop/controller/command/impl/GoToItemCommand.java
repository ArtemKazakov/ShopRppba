package by.bsuir.shop.controller.command.impl;

import by.bsuir.shop.controller.command.Command;
import by.bsuir.shop.controller.command.exception.CommandException;
import by.bsuir.shop.entity.Review;
import by.bsuir.shop.entity.ShopItem;
import by.bsuir.shop.service.ItemService;
import by.bsuir.shop.service.ReviewService;
import by.bsuir.shop.service.exception.ServiceException;
import by.bsuir.shop.service.factory.ServiceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Command that loads item<br/>
 * Implements {@link Command}
 */
public class GoToItemCommand implements Command {
    public final static String SRC = "/WEB-INF/jsp/item.jsp";
    public final static String ATTR_ID = "item_id";
    public final static String ATTR_ITEM = "item";
    public final static String ATTR_REVIEWS = "reviews";

    private final static Logger logger = LogManager.getLogger(GoToItemCommand.class.getName());

    /**
     * Gets item info
     * @param request incoming request.
     * @return link to item's page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ServiceFactory factory = ServiceFactory.getInstance();
        ItemService itemService = factory.getItemService();
        ReviewService reviewService = factory.getReviewService();

        String id = request.getParameter(ATTR_ID);

        System.out.println(id);
        try {
            ShopItem item = itemService.loadItem(id);
            List<Review> reviews = reviewService.getItemReviews(id);

            request.setAttribute(ATTR_ITEM, item);
            request.setAttribute(ATTR_REVIEWS, reviews);
        } catch (ServiceException ex){
            logger.error(ex);
            throw new CommandException(ex);
        }

        return SRC;
    }
}
