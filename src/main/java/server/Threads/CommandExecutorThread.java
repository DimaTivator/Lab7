package server.Threads;

import commonModule.commands.CommandWithResponse;
import commonModule.commands.CommandsExecutor;
import commonModule.dataStructures.Request;
import commonModule.dataStructures.Response;
import org.slf4j.Logger;
import server.NetworkProvider;
import server.collectionManagement.CollectionManager;

public class CommandExecutorThread extends Thread {

    public CommandExecutorThread(Request request, Logger logger, CommandsExecutor commandsExecutor,
                                 NetworkProvider networkProvider, CollectionManager collectionManager, String clientsDataPath) {
        super(() -> {

            Response response = null;

            try {
                commandsExecutor.execute((CommandWithResponse) request.getCommand());
                response = commandsExecutor.getCommandResponse();
                logger.info("Command {} successfully executed", request.getCommand().getClass());

            } catch (Exception e) {
                logger.info("Command {} threw the exception: {}", request.getCommand().getClass(), e.getClass());
                response = new Response("Exception", null, e.getMessage());

            } finally {
                // TODO save
                // collectionManager.save(clientsDataPath);
                Thread responseSenderThread = new ResponseSenderThread(response, networkProvider, request.getHost());
                responseSenderThread.start();
            }
        });
    }
}
