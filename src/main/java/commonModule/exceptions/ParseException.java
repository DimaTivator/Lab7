package commonModule.exceptions;

import commonModule.auxiliaryClasses.ConsoleColors;

/**
 * The ParseException class represents an exception that occurs during parsing.
 * It is used to indicate an error in the format of an input string.
 */
public class ParseException extends Exception {
    /**
    * Constructs a new ParseException with the specified error message.
    * @param message the error message.
    */
    public ParseException(String message) {
        super(ConsoleColors.RED + message + ConsoleColors.RESET);
    }
}
