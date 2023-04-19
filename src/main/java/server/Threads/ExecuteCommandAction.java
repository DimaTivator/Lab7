package server.Threads;

import commonModule.commands.CommandTemplate;
import commonModule.commands.CommandWithResponse;
import commonModule.commands.CommandsExecutor;
import commonModule.dataStructures.network.CommandRequest;
import commonModule.dataStructures.network.CommandResponse;
import commonModule.dataStructures.network.Request;
import commonModule.dataStructures.network.Response;
import org.slf4j.Logger;
import server.NetworkProvider;
import server.collectionManagement.CollectionManager;
import server.database.DatabaseHandler;
import server.database.DatabaseManager;

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

        CommandTemplate command = (CommandTemplate) request.getCommand();

        try {
            command.setDatabaseHandler(databaseHandler);
            command.setDatabaseManager(databaseManager);
            command.setUserLogin(request.getLogin());

            commandsExecutor.execute((CommandWithResponse) command);
            response = commandsExecutor.getCommandResponse();
            logger.info("Command {} successfully executed", command.getClass());

        } catch (Exception e) {
            logger.info("Command {} threw the exception: {}", command.getClass(), e.getClass());
            response = new CommandResponse("Exception", null, e.getMessage());

        } finally {
            Thread responseSenderThread = new ResponseSenderThread(response, networkProvider, request.getHost());
            responseSenderThread.start();
        }
    }
}
