package commonModule.commands;

import commonModule.commands.commandObjects.*;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CollectionPrinter;
import commonModule.dataStructures.Response;
import commonModule.dataStructures.Triplet;
import commonModule.exceptions.ScriptsRecursionException;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.io.consoleIO.CommandParser;
import commonModule.io.fileIO.out.HumanBeingXMLWriter;
import commonModule.collectionClasses.HumanBeing;

import java.util.*;

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
        // fillCommandLists();
    }

    private boolean checkCollectionChanges(Map<Long, HumanBeing> collectionBeforeCommand, Map<Long, HumanBeing> collectionAfterCommand) {
        boolean equals = (collectionAfterCommand.size() == collectionBeforeCommand.size());
        for (Long key : collectionAfterCommand.keySet()) {
            if (!collectionBeforeCommand.containsKey(key) || !collectionBeforeCommand.get(key).equals(collectionAfterCommand.get(key))) {
                equals = false;
            }
        }
        return equals;
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
