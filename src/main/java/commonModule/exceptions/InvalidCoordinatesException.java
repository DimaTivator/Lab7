package commonModule.exceptions;

import commonModule.auxiliaryClasses.ConsoleColors;

/**
 * The {@code InvalidCoordinatesException} class is an exception that is thrown when the coordinates provided are invalid.
 * The exception message can be customized by passing a string as an argument.
 */
public class InvalidCoordinatesException extends Exception {

    /**
     * Constructs a new exception with the default message.
     */
    public InvalidCoordinatesException() {
        super(ConsoleColors.RED + "x coordinate must be < 487 and y coordinate must be > -704\n" + ConsoleColors.RESET);
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public InvalidCoordinatesException(String message) {
        super(ConsoleColors.RED + message + ConsoleColors.RESET);
    }

}
