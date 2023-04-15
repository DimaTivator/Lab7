package commonModule.exceptions;

import commonModule.auxiliaryClasses.ConsoleColors;

/**
 * Represents an exception that is thrown when an invalid input is encountered.
 */
public class InvalidInputException extends Exception {
    /**
     Constructs a new {@code InvalidInputException} with the specified detail message.
     @param message the detail message.
     */
    public InvalidInputException(String message) {
        super(ConsoleColors.RED + message + ConsoleColors.RESET);
    }
}
