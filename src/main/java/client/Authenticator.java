package client;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.exceptions.InvalidInputException;

import java.io.Console;
import java.util.Objects;
import java.util.Scanner;

public class Authenticator {

    private final NetworkProvider networkProvider;
    private String login;
    private String password;
    private final Scanner scanner;

    public Authenticator(NetworkProvider networkProvider, Scanner scanner) {
        this.networkProvider = networkProvider;
        this.scanner = scanner;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private void readPassword() throws InvalidInputException {

        Console console = System.console();

        System.out.print(ConsoleColors.BLUE_BRIGHT + "Enter password: " + ConsoleColors.RESET);

        // if console is available
        if (console != null) {
            password = String.valueOf(console.readPassword());
        } else {
            password = scanner.nextLine();
        }

        if (password.equals("")) {
            throw new InvalidInputException("Password can't be empty string! Please try to enter a password again");
        }
    }

    private void readLogin() throws InvalidInputException {

        System.out.print(ConsoleColors.BLUE_BRIGHT + "Enter login: " + ConsoleColors.RESET);
        login = scanner.nextLine();

        if (login.equals("")) {
            throw new InvalidInputException("Login can't be empty string! Please try to enter a password again");
        }
    }


    public void authenticate() {

        while (true) {

            System.out.println(ConsoleColors.BLUE_BRIGHT + "Choose an option: 1 - sign up, 2 - log in: " + ConsoleColors.RESET);

            int option = 0;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "Option is a number 1 or 2" + ConsoleColors.RESET);
            }


        }
    }
}
