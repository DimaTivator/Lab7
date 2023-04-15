package commonModule.io.consoleIO;

import java.util.Scanner;

/**
 ConsoleReader is an abstract class which provides the basic functionality for reading data from the console.
 It provides a {@link Scanner} object to read data from the console.
 @param <T> the type of the object to be read from the console
 */
public abstract class ConsoleReader<T> {

    private final Scanner consoleScanner = new Scanner(System.in);

    /**
     Returns the {@link Scanner} object to read data from the console.
     */
    public Scanner getConsoleScanner() {
        return consoleScanner;
    }

    /**
     Reads an object of type T from the console.
     @return an object of type T read from the console
     @throws Exception in case of error while reading the object from the console
     */
    public abstract T readObjectFromConsole() throws Exception;
}
