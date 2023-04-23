package commonModule.exceptions.commandExceptions;

import commonModule.auxiliaryClasses.ConsoleColors;

/**
 * Exception NoSuchCommandException is thrown when CommandExecutor can't find a parsed command in the command list
 */
public class NoSuchCommandException extends Exception {

    /**
     * Default constructor which sets a default error message.
     */
    public NoSuchCommandException() {
        super(ConsoleColors.RED + "I don't have such command yet :(\nPlease try to enter a command again" + ConsoleColors.RESET);
    }

    /**
     * Parameterized constructor which allows to set custom error message.
     *
     * @param message Custom error message.
     */
    public NoSuchCommandException(String message) {
        super(ConsoleColors.RED + message + ConsoleColors.RESET);
    }
}
