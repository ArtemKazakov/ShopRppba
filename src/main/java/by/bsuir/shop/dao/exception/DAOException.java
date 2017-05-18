package by.bsuir.shop.dao.exception;

/**
 * Class {@code DAOException} is the class, that extends {@code Exception} class to get own exceptions for "DAO" layer.
 */
public class DAOException extends Exception{
    public DAOException(){}
    public DAOException(String message, Throwable exception) {
        super(message, exception);
    }
    public DAOException(String message) {
        super(message);
    }
    public DAOException(Throwable exception) {
        super(exception);
    }
}
