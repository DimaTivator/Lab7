package server.Threads;

import commonModule.commands.CommandsExecutor;
import commonModule.dataStructures.Request;
import org.slf4j.Logger;
import server.NetworkProvider;
import server.collectionManagement.CollectionManager;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


public class ClientHandlerThread extends Thread {

    public ClientHandlerThread(NetworkProvider networkProvider, Logger logger, CommandsExecutor commandsExecutor,
                               CollectionManager collectionManager, String clientsDataPath) {

        super(() -> {

           ForkJoinPool pool = new ForkJoinPool();

           while (true) {

               Request request = networkProvider.receive();

               if (request == null) {
                   continue;
               }

               RecursiveAction executeCommandAction = new ExecuteCommandAction(
                       request,
                       logger,
                       commandsExecutor,
                       networkProvider,
                       collectionManager,
                       clientsDataPath
               );

               pool.invoke(executeCommandAction);
           }
        });
    }
}
