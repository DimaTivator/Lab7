package commonModule.exceptions;

import commonModule.auxiliaryClasses.ConsoleColors;

/**
 * The NoSuchKeyException class is an exception class used
 * to indicate that a specified key was not found in the collection.
 */
public class NoSuchKeyException extends Exception {
    /**
     Constructs a NoSuchKeyException with the specified message.
     @param message the message that should be displayed when the exception is thrown.
     */
    public NoSuchKeyException(String message) {
        super(ConsoleColors.RED + message + ConsoleColors.RESET);
    }
}
