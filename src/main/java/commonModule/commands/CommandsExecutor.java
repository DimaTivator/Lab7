package commonModule.commands;

import server.collectionManagement.CollectionManager;
import server.collectionManagement.CollectionPrinter;
import commonModule.dataStructures.network.Response;


/**
 * The `CommandsExecutor` class is responsible for executing commands.
 * It uses two objects of the `CollectionPrinter` and `CollectionManager` classes.
 */
public class CommandsExecutor {

    private Response response;


    /**
     * The `collectionPrinter` object is used for printing various information about the collection.
     */
    private final CollectionPrinter collectionPrinter;

    /**
     * The `collectionManager` object is used for managing the collection.
     */
    private final CollectionManager collectionManager;


    /**
     * Constructs a `CommandsExecutor` object with the given `collectionManager` and `collectionPrinter`.
     *
     * @param collectionManager the `CollectionManager` object used for managing the collection
     * @param collectionPrinter the `CollectionPrinter` object used for printing information about the collection
     */
    public CommandsExecutor(CollectionManager collectionManager, CollectionPrinter collectionPrinter) {
        this.collectionManager = collectionManager;
        this.collectionPrinter = collectionPrinter;
    }

    /**
     Executes the given command.

     @throws Exception if an error occurs during the execution of the command
     */
    public void execute(CommandWithResponse command) throws Exception {

        command.setCollectionManager(collectionManager);
        command.setCollectionPrinter(collectionPrinter);

        command.execute();

        response = command.getCommandResponse();
    }

    public Response getCommandResponse() {
        return response;
    }
}
