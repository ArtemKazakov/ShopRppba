package by.bsuir.shop.service.factory;

import by.bsuir.shop.service.*;
import by.bsuir.shop.service.impl.*;


public class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private UserService userService = new UserServiceImpl();
    private ItemService itemService = new ItemServiceImpl();
    private FileUploadService fileUploadService = new FileUploadServiceImpl();
    private CartService cartService = new CartServiceImpl();
    private OrderService orderService = new OrderServiceImpl();
    private ReviewService reviewService = new ReviewServiceImpl();
    private SubscriptionService subscriptionService = new SubscriptionServiceImpl();

    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return INSTANCE;
    }

    public UserService getUserService(){
        return userService;
    }

    public ItemService getItemService(){
        return itemService;
    }

    public FileUploadService getFileUploadService(){
        return fileUploadService;
    }

    public CartService getCartService() {
        return cartService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public ReviewService getReviewService() {
        return reviewService;
    }
    public SubscriptionService getSubscriptionService() {
        return subscriptionService;
    }
}
