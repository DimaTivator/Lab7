package commonModule.exceptions;

import commonModule.auxiliaryClasses.ConsoleColors;

/**
 * The {@code OccupiedKeyException} class is an exception that is thrown when an attempt
 * is made to add an element to the collection with a key that already exists in the collection.
 */
public class OccupiedKeyException extends Exception {
    /**
     * Constructs a new {@code OccupiedKeyException} with the specified detail message.
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public OccupiedKeyException(String message) {
        super(ConsoleColors.RED + message + ConsoleColors.RESET);
    }
}