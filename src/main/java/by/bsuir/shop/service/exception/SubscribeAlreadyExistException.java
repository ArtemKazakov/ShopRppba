package by.bsuir.shop.service.exception;

public class SubscribeAlreadyExistException extends ServiceException {
    public SubscribeAlreadyExistException(){}
    public SubscribeAlreadyExistException(String message, Throwable exception) {
        super(message, exception);
    }
    public SubscribeAlreadyExistException(String message) {
        super(message);
    }
    public SubscribeAlreadyExistException(Throwable exception) {
        super(exception);
    }
}