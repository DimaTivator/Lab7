package commonModule.exceptions;

import commonModule.auxiliaryClasses.ConsoleColors;

public class ObjectAccessException extends Exception {

    public ObjectAccessException() {
        super(ConsoleColors.RED + "You don't have access to this object" + ConsoleColors.RESET);
    }
}
