package server.Threads;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.commands.CommandTemplate;
import commonModule.commands.CommandWithResponse;
import commonModule.commands.CommandsExecutor;
import commonModule.dataStructures.network.*;
import org.slf4j.Logger;
import server.NetworkProvider;
import server.collectionManagement.CollectionManager;
import server.database.DatabaseHandler;
import server.database.DatabaseManager;

import java.sql.SQLException;
import java.util.concurrent.RecursiveAction;

public class ExecuteCommandAction extends RecursiveAction {

    private final CommandRequest request;
    private final Logger logger;
    private final CommandsExecutor commandsExecutor;
    private final NetworkProvider networkProvider;
    private final CollectionManager collectionManager;
    private final DatabaseHandler databaseHandler;
    private final DatabaseManager databaseManager;

    public ExecuteCommandAction(CommandRequest request, Logger logger, CommandsExecutor commandsExecutor,
                                NetworkProvider networkProvider, CollectionManager collectionManager,
                                DatabaseManager databaseManager, DatabaseHandler databaseHandler) {
        this.request = request;
        this.logger = logger;
        this.commandsExecutor = commandsExecutor;
        this.networkProvider = networkProvider;
        this.collectionManager = collectionManager;
        this.databaseManager = databaseManager;
        this.databaseHandler = databaseHandler;
    }

    @Override
    protected void compute() {

        Response response = null;

        try {
            if (!request.getPassword().equals(databaseHandler.getUsersPassword(request.getLogin()))) {
                System.out.println(request.getLogin());
                System.out.println(request.getPassword());
                response = new CommandResponse("Exception", null,
                        ConsoleColors.RED + "Authentication error" + ConsoleColors.RESET);

                Thread responseSenderThread = new ResponseSenderThread(response, networkProvider, request.getHost());
                responseSenderThread.start();
                return;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return;
        }

        CommandTemplate command = (CommandTemplate) request.getCommand();

        try {
            command.setDatabaseHandler(databaseHandler);
            command.setDatabaseManager(databaseManager);
            command.setUserLogin(request.getLogin());

            commandsExecutor.execute((CommandWithResponse) command);
            response = commandsExecutor.getCommandResponse();
            logger.info("Command {} successfully executed", command.getClass());

        } catch (SQLException e) {
            logger.error("SQLException {} while executing command {}", e.getMessage(), command.getClass());
            response = new CommandResponse("Exception", null,
                    "An error occurred when updating the database\nTry to send your request later");

        } catch (Exception e) {
            logger.error("Command {} threw the exception: {}", command.getClass(), e.getClass());
            response = new CommandResponse("Exception", null, e.getMessage());

        } finally {
            Thread responseSenderThread = new ResponseSenderThread(response, networkProvider, request.getHost());
            responseSenderThread.start();
        }
    }
}
