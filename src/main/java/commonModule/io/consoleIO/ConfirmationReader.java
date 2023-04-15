package commonModule.io.consoleIO;

import java.util.Scanner;

/**
 * ConfirmationReader is a class that reads confirmation input from the console.
 * It reads the input and returns a string representing the confirmation.x
 */
public class ConfirmationReader extends ConsoleReader<String> {
    /**
     This method reads the confirmation from the console and returns it as a string.
     @return String representing the confirmation entered by the user.
     */
    @Override
    public String readObjectFromConsole() {
        System.out.println("Enter Y / N to confirm");
        Scanner scanner = getConsoleScanner();
        return scanner.nextLine();
    }
}