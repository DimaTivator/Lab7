package server.Threads;

import commonModule.commands.CommandWithResponse;
import commonModule.commands.CommandsExecutor;
import commonModule.dataStructures.network.CommandRequest;
import commonModule.dataStructures.network.CommandResponse;
import commonModule.dataStructures.network.Request;
import commonModule.dataStructures.network.Response;
import org.slf4j.Logger;
import server.NetworkProvider;
import server.collectionManagement.CollectionManager;

import java.util.concurrent.RecursiveAction;

public class ExecuteCommandAction extends RecursiveAction {

    private final CommandRequest request;
    private final Logger logger;
    private final CommandsExecutor commandsExecutor;
    private final NetworkProvider networkProvider;
    private final CollectionManager collectionManager;
    private final String clientsDataPath;

    public ExecuteCommandAction(CommandRequest request, Logger logger, CommandsExecutor commandsExecutor,
                                NetworkProvider networkProvider, CollectionManager collectionManager, String clientsDataPath) {
        this.request = request;
        this.logger = logger;
        this.commandsExecutor = commandsExecutor;
        this.networkProvider = networkProvider;
        this.collectionManager = collectionManager;
        this.clientsDataPath = clientsDataPath;
    }

    @Override
    protected void compute() {

        Response response = null;

        try {
            commandsExecutor.execute((CommandWithResponse) request.getCommand());
            response = commandsExecutor.getCommandResponse();
            logger.info("Command {} successfully executed", request.getCommand().getClass());

        } catch (Exception e) {
            logger.info("Command {} threw the exception: {}", request.getCommand().getClass(), e.getClass());
            response = new CommandResponse("Exception", null, e.getMessage());

        } finally {
            collectionManager.save(clientsDataPath);
            Thread responseSenderThread = new ResponseSenderThread(response, networkProvider, request.getHost());
            responseSenderThread.start();
        }
    }
}
