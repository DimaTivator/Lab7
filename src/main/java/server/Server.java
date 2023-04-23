package server;

import commonModule.auxiliaryClasses.ConsoleColors;
import server.Threads.ClientHandlerThread;
import server.Threads.ConsoleInputThread;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CollectionPrinter;
import commonModule.commands.CommandsExecutor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.BindException;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.database.DataLoader;
import server.database.DatabaseHandler;
import server.database.DatabaseManager;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new FileReader("db_config"));
        } catch (FileNotFoundException e) {
            System.out.println(ConsoleColors.RED + "Can't found the file `db_config`" + ConsoleColors.RESET);
            System.exit(0);
        }

        String username = null;
        String password = null;

        try {
            username = fileScanner.nextLine().trim();
            password = fileScanner.nextLine().trim();
        } catch (NoSuchElementException e) {
            System.out.println("Unable to read username and password from db_config");
            System.exit(0);
        }

        DatabaseHandler databaseHandler = new DatabaseHandler("jdbc:postgresql://localhost:5432/studs", username, password);
        databaseHandler.connect();


        if (args.length == 0) {
            System.out.println(ConsoleColors.RED + "You should enter port as an argument!" + ConsoleColors.RESET);
            System.exit(0);
        }

        int port = 0;

        NetworkProvider networkProvider = null;

        try {

            port = Integer.parseInt(args[0]);
            networkProvider = new NetworkProvider(port);

        } catch (BindException e) {
            System.out.println(ConsoleColors.RED + "Port is busy. Try to enter another port" + ConsoleColors.RESET);
            logger.error(e.getMessage());
            System.exit(0);

        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "Port must be a number!" + ConsoleColors.RESET);
            logger.error(e.getMessage());
            System.exit(0);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            System.exit(0);
        }


        System.out.println("Server started on port " + port);
        logger.info("Server started on port {}", port);

        CollectionManager collectionManager = new CollectionManager();
        CollectionPrinter collectionPrinter = new CollectionPrinter();
        CommandsExecutor commandsExecutor = new CommandsExecutor(collectionManager, collectionPrinter);

        DataLoader dataLoader = new DataLoader(databaseHandler);

        DatabaseManager databaseManager = new DatabaseManager(databaseHandler, dataLoader);

        try {
            collectionManager.setCollection(Collections.synchronizedMap(dataLoader.loadCollection()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            System.exit(0);
        }

        try {
            collectionManager.setElementsOwners(Collections.synchronizedMap(dataLoader.loadElementsOwners()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            System.exit(0);
        }

        ServerCommandExecutor serverCommandExecutor = new ServerCommandExecutor(collectionManager);


        logger.info("Data parsed from database");

        Thread consoleInputThread = new ConsoleInputThread(serverCommandExecutor, logger);
        consoleInputThread.start();

        try {

            Thread clientHandlerThread = new ClientHandlerThread(
                    databaseHandler,
                    databaseManager,
                    networkProvider,
                    logger,
                    commandsExecutor,
                    collectionManager
            );

            clientHandlerThread.start();

        } catch (NullPointerException ignored) {}

          catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

