package server;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.collectionClasses.HumanBeing;
import commonModule.commands.CommandWithResponse;
import commonModule.dataStructures.Pair;
import commonModule.io.consoleIO.CommandParser;
import commonModule.io.fileIO.in.HumanBeingXMLParser;
import commonModule.io.fileIO.in.Parser;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CollectionPrinter;
import commonModule.dataStructures.Request;
import commonModule.dataStructures.Response;
import commonModule.io.consoleIO.ConfirmationReader;
import commonModule.commands.CommandsExecutor;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println(ConsoleColors.RED + "You should enter port as an argument!" + ConsoleColors.RESET);
            System.exit(0);
        }

        int port = 0;

        server.NetworkProvider networkProvider = null;

        try {
            port = Integer.parseInt(args[0]);
            networkProvider = new server.NetworkProvider(port);
        } catch (BindException e) {
            System.out.println(ConsoleColors.RED + "Port in busy. Try to enter another port" + ConsoleColors.RESET);
            System.exit(0);
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "Port must be a number!" + ConsoleColors.RESET);
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }


        System.out.println("Server started on port " + port);
        logger.info("Server started on port {}", port);

        CollectionManager collectionManager = new CollectionManager();
        CollectionPrinter collectionPrinter = new CollectionPrinter();
        CommandsExecutor commandsExecutor = new CommandsExecutor(collectionManager, collectionPrinter);

        ServerCommandExecutor serverCommandExecutor = new ServerCommandExecutor(collectionManager);

        Scanner scanner = new Scanner(System.in);
        Parser<LinkedHashMap<Long, HumanBeing>> humanBeingXMLParser = new HumanBeingXMLParser();


        String clientsDataPath = "";
        if (args.length > 1) {
            try {
                clientsDataPath = args[1];
                serverCommandExecutor.setClientsDataPath(clientsDataPath);
                collectionManager.setCollection(humanBeingXMLParser.parseData(clientsDataPath));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        } else {
            while (true) {
                try {
                    System.out.print(ConsoleColors.BLUE + "Enter a path to file with data: " + ConsoleColors.RESET);
                    clientsDataPath = scanner.nextLine();

                    serverCommandExecutor.setClientsDataPath(clientsDataPath);
                    collectionManager.setCollection(humanBeingXMLParser.parseData(clientsDataPath));
                    break;

                } catch (Exception ignored) {
                }
            }
        }

        logger.info("Data parsed from {}", clientsDataPath);

        try {

            while (true) {

                Request request = networkProvider.receive();

                // check is there any input in System.in
                try {

                    SignalHandler signalHandler = signal -> {
                        if (signal.getName().equals("INT")) {
                            System.exit(0);
                        }
                    };
                    Signal.handle(new Signal("INT"), signalHandler);

                    if (System.in.available() > 0) {

                        List<String> line = Arrays.stream(scanner.nextLine().strip().replaceAll(" +", " ").split(" ")).toList();
                        Pair<String, String[]> commandPair = new Pair<>(line.get(0), line.subList(1, line.size()).toArray(new String[0]));

                        logger.info("Server command `{}` successfully read", commandPair.getFirst());

                        serverCommandExecutor.execute(commandPair);
                    }

                } catch (Exception e) {
                    logger.error(e.getMessage());
                }

                // if now the request is null then go to the next iteration
                if (request == null) {
                    continue;
                }

                Response response = null;

                try {

                    commandsExecutor.execute((CommandWithResponse) request.getCommand());
                    logger.info("Command {} successfully executed", request.getCommand().getClass());

                    response = commandsExecutor.getCommandResponse();

                } catch (Exception e) {
                    logger.info("Command {} threw the exception: {}", request.getCommand().getClass(), e.getClass());
                    response = new Response("Exception", null, e.getMessage());

                } finally {
                    networkProvider.send(response, request.getHost());

                    collectionManager.save(clientsDataPath);
                    logger.info("Data successfully saved to {}", clientsDataPath);
                }

            }

        } catch (NullPointerException ignored) {}

          catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

