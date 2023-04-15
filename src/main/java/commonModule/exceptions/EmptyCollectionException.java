package commonModule.exceptions;

import commonModule.auxiliaryClasses.ConsoleColors;

/**
 * The {@code EmptyCollectionException} class represents an exception that is thrown when a
 * collection is empty and an operation can't be performed.
 */
public class EmptyCollectionException extends Exception {

    /**
     * Constructs an instance of the {@code EmptyCollectionException} class with a specified error message.
     *
     * @param message the error message
     */
    public EmptyCollectionException(String message) {
        super(ConsoleColors.RED + message + ConsoleColors.RESET);
    }

    /**
     * Constructs an instance of the {@code EmptyCollectionException} class with a default error message.
     */
    public EmptyCollectionException() {
        super(ConsoleColors.RED + "Collection is empty :(" + ConsoleColors.RESET);
    }
}
