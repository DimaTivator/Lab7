package server.Threads;

import commonModule.commands.CommandsExecutor;
import commonModule.dataStructures.Request;
import org.slf4j.Logger;
import server.NetworkProvider;
import server.collectionManagement.CollectionManager;


public class ClientHandlerThread extends Thread {

    public ClientHandlerThread(NetworkProvider networkProvider, Logger logger, CommandsExecutor commandsExecutor,
                               CollectionManager collectionManager, String clientsDataPath) {

        super(() -> {

           while (true) {

               Request request = networkProvider.receive();

               if (request == null) {
                   continue;
               }

               Thread commandExecutorThread = new CommandExecutorThread(
                       request,
                       logger,
                       commandsExecutor,
                       networkProvider,
                       collectionManager,
                       clientsDataPath
               );

               commandExecutorThread.start();
           }
        });
    }
}
