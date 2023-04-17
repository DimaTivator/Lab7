package server.Threads;

import commonModule.commands.CommandWithResponse;
import commonModule.commands.CommandsExecutor;
import commonModule.dataStructures.Request;
import commonModule.dataStructures.Response;
import org.slf4j.Logger;
import server.NetworkProvider;
import server.collectionManagement.CollectionManager;

import java.util.concurrent.RecursiveAction;

public class ExecuteCommandAction extends RecursiveAction {

    private final Request request;
    private final Logger logger;
    private final CommandsExecutor commandsExecutor;
    private final NetworkProvider networkProvider;
    private final CollectionManager collectionManager;
    private final String clientsDataPath;

    public ExecuteCommandAction(Request request, Logger logger, CommandsExecutor commandsExecutor,
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
            response = new Response("Exception", null, e.getMessage());

        } finally {
            collectionManager.save(clientsDataPath);
            Thread responseSenderThread = new ResponseSenderThread(response, networkProvider, request.getHost());
            responseSenderThread.start();
        }
    }
}
