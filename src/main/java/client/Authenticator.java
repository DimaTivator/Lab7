package client;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.dataStructures.network.AuthenticationRequest;
import commonModule.dataStructures.network.AuthenticationResponse;
import commonModule.dataStructures.network.Request;
import commonModule.exceptions.InvalidInputException;

import java.io.Console;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Authenticator {

    private final NetworkProvider networkProvider;
    private final Scanner scanner = new Scanner(System.in);
    private String login;
    private String password;

    public Authenticator(NetworkProvider networkProvider) {
        this.networkProvider = networkProvider;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    private String readPassword(int attempt) throws InvalidInputException {

        Console console = System.console();

        System.out.print(
                ConsoleColors.BLUE_BRIGHT +
                String.format("Enter password %s: ", (attempt == 1 ? "" : "again")) +
                ConsoleColors.RESET
        );

        String password;
        // if console is available
        if (console != null) {
            password = String.valueOf(console.readPassword());
        } else {
            password = scanner.nextLine();
        }

        return password;
    }


    /**
     * Method encodes password using the SHA-256 algorithm
     */
    public String encodePassword(String password) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            // Apply SHA-256 hashing to the password
            byte[] hashedBytes = messageDigest.digest(password.getBytes());

            // Convert the hashed bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


    private String readLogin() throws InvalidInputException {

        System.out.print(ConsoleColors.BLUE_BRIGHT + "Enter login: " + ConsoleColors.RESET);
        String login = scanner.nextLine();

        if (login.equals("")) {
            throw new InvalidInputException("Login can't be an empty string! Please try to enter a password again");
        }

        return login;
    }


    public void authenticate() {

        while (true) {

            try {

                System.out.print(ConsoleColors.BLUE_BRIGHT + "Choose an option: 1 - sign up, 2 - log in: " + ConsoleColors.RESET);

                int option = 0;
                try {
                    option = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    throw new InvalidInputException(ConsoleColors.RED + "Option is a number 1 or 2" + ConsoleColors.RESET);
                }

                Request authenticationRequest;

                if (option == 1) {

                    login = readLogin();
                    String password1 = readPassword(1);
                    String password2 = readPassword(2);

                    if (password1.equals(password2)) {
                        password = password1;;
                        authenticationRequest = new AuthenticationRequest(true, login, encodePassword(password));
                    } else {
                        throw new InvalidInputException("Passwords must the same! Please try to sign up again");
                    }

                } else if (option == 2) {
                    login = readLogin();
                    password = readPassword(1);
                    authenticationRequest = new AuthenticationRequest(false, login, encodePassword(password));

                } else {
                    throw new InvalidInputException(ConsoleColors.RED + "Option is a number 1 or 2" + ConsoleColors.RESET);
                }

                networkProvider.send(authenticationRequest);

                AuthenticationResponse response = (AuthenticationResponse) networkProvider.receive();

                if (response == null) {
                    System.out.println(ConsoleColors.RED + "Server is down :(\nPlease try again later" + ConsoleColors.RESET);
                } else if (response.isAuthenticated()) {
                    System.out.println(ConsoleColors.GREEN + "Authentication was successful" + ConsoleColors.RESET);
                    break;
                } else {
                    System.out.println(response.getException().getMessage());
                }

            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
