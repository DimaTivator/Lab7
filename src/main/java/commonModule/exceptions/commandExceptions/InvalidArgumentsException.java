package commonModule.exceptions.commandExceptions;

import commonModule.auxiliaryClasses.ConsoleColors;

/**
 * Exception class to handle invalid arguments in the command.
 */
public class InvalidArgumentsException extends Exception {

    /**
     * Default constructor which sets a default error message.
     */
    public InvalidArgumentsException() {
        super(ConsoleColors.RED + "Something wrong with command arguments :(\nPlease try to enter a command again" + ConsoleColors.RESET);
    }

    /**
     * Parameterized constructor which allows to set custom error message.
     *
     * @param message Custom error message.
     */
    public InvalidArgumentsException(String message) {
        super(message);
    }
}
