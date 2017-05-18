package by.bsuir.shop.controller.command.exception;

/**
 * Class {@code CommandException} is the class, that extends {@code Exception} class to get own exceptions for "Command" layer.
 */
public class CommandException extends Exception {
    public CommandException(){}
    public CommandException(String message, Throwable exception) {
        super(message, exception);
    }
    public CommandException(String message) {
        super(message);
    }
    public CommandException(Throwable exception) {
        super(exception);
    }
}
