package client;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.commands.Command;
import commonModule.dataStructures.network.*;
import commonModule.dataStructures.Triplet;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.io.consoleIO.CommandParser;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

public class Client {

    public static void main(String[] args) {

        InetAddress address = null;
        int port = 0;

        try {
            address = InetAddress.getByName(args[0]);
        } catch (UnknownHostException e) {
            System.out.println(ConsoleColors.RED + "Unknown host. Please try again" + ConsoleColors.RESET);
            System.exit(0);
        }

        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "Port must be a number. Please try again" + ConsoleColors.RESET);
            System.exit(0);
        }


        String commandName;
        String[] commandArgs;
        CommandRequest request;
        Response response;
        SizeResponse sizeResponse = null;
        int numberOfPackages = 1;

        try {

            NetworkProvider networkProvider = new NetworkProvider(address.getHostAddress(), port);
            CommandParser commandParser = new CommandParser();
            ScriptExecutor scriptExecutor = new ScriptExecutor();

            // User authentication
            Authenticator authenticator = new Authenticator(networkProvider);
            authenticator.authenticate();


            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-exec")) {
                    if (i + 1 < args.length) {
                        String filePath = args[i + 1];
                        Path path = Paths.get(filePath);
                        if (Files.exists(path)) {
                            scriptExecutor.executeScript(filePath, networkProvider, authenticator);
                        } else {
                            System.out.printf(ConsoleColors.RED + "File %s does not exists!\n" + ConsoleColors.RESET, filePath);
                            System.exit(0);
                        }
                    } else {
                        System.out.println(ConsoleColors.RED + "You should write a path to the script after `-exec`" + ConsoleColors.RESET);
                        System.exit(0);
                    }
                }
            }

            System.out.println("If you want to see the list of available commands, enter 'help'");

            while (true) {

                try {

                    Triplet<String, String[], Object> parsedInput = commandParser.readObjectFromConsole();
                    commandName = parsedInput.getFirst();
                    commandArgs = parsedInput.getSecond();
                    Object object = parsedInput.getThird();

                    if (commandName.equals("exit")) {
                        if (commandArgs.length == 0) {
                            System.out.println(ConsoleColors.PURPLE + "Bye!" + ConsoleColors.RESET);
                            System.exit(0);
                        } else {
                            throw new InvalidArgumentsException("Command exit doesn't take any arguments!");
                        }
                    }

                    else if (commandName.equals("execute_script")) {
                        if (commandArgs.length == 1) {
                            scriptExecutor.executeScript(commandArgs[0], networkProvider, authenticator);
                        } else {
                            throw new InvalidArgumentsException("Command execute_script take only one argument - path to the script");
                        }

                    } else {

                        Command command = commandParser.pack(parsedInput);

                        request = new CommandRequest(command);
                        request.setLogin(authenticator.getLogin());
                        request.setPassword(authenticator.getPassword());

                        networkProvider.send(request);
                        response = networkProvider.receive();

                        if (response == null) {
                            System.out.println(ConsoleColors.RED + "Server is down :(\nPlease try again later" + ConsoleColors.RESET);
                        }

                        if (response.getClass() == SizeResponse.class) {

                            sizeResponse = (SizeResponse) response;
                            numberOfPackages = Integer.parseInt(sizeResponse.getSize());

                            for (int i = 0; i < numberOfPackages; i++) {
                                response = networkProvider.receive();
                                if (response == null) {
                                    System.out.println(ConsoleColors.RED + "Server is down :(\nPlease try again later" + ConsoleColors.RESET);
                                } else {
                                    System.out.println(((CommandResponse)response).getOutput());
                                }
                            }

                        } else {
                            System.out.println(((CommandResponse)response).getOutput());
                        }
                    }

                } catch (NoSuchElementException e) {
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
