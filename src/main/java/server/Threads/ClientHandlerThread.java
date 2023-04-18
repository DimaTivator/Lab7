package server.Threads;

import commonModule.commands.CommandsExecutor;
import commonModule.dataStructures.network.AuthenticationRequest;
import commonModule.dataStructures.network.CommandRequest;
import commonModule.dataStructures.network.CommandResponse;
import commonModule.dataStructures.network.Request;
import org.slf4j.Logger;
import server.NetworkProvider;
import server.collectionManagement.CollectionManager;
import server.database.DatabaseHandler;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


public class ClientHandlerThread extends Thread {

    public ClientHandlerThread(DatabaseHandler databaseHandler,NetworkProvider networkProvider, Logger logger,
                               CommandsExecutor commandsExecutor, CollectionManager collectionManager, String clientsDataPath) {

        super(() -> {

           ForkJoinPool pool = new ForkJoinPool();

           while (true) {

               Request request = networkProvider.receive();

               if (request == null) {
                   continue;
               }

               if (request.getClass() == CommandRequest.class) {

                   RecursiveAction executeCommandAction = new ExecuteCommandAction(
                           (CommandRequest) request,
                           logger,
                           commandsExecutor,
                           networkProvider,
                           collectionManager,
                           clientsDataPath
                   );

                   pool.invoke(executeCommandAction);

               } else if (request.getClass() == AuthenticationRequest.class) {
                    Thread authenticationThread = new AuthenticationThread(
                            databaseHandler,
                            networkProvider,
                            logger,
                            (AuthenticationRequest) request
                    );
                    authenticationThread.start();
               }
           }
        });
    }
}
