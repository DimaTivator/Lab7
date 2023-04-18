package commonModule.commands.commandObjects;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.commands.CommandTemplate;
import commonModule.dataStructures.network.CommandResponse;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CollectionPrinter;
import commonModule.dataStructures.network.Response;
import commonModule.commands.CommandWithResponse;

/**
 * InfoCommand is a class that provides information about the collection,
 * such as the type of the collection, the date of initialization, and the number of elements in the collection.
 */
public class InfoCommand extends CommandTemplate implements CommandWithResponse {

    private StringBuilder output;

    /**
     * Constructs a new InfoCommand with the specified CollectionManager and CollectionPrinter.
     * @param collectionManager the CollectionManager that manages the collection
     * @param collectionPrinter the CollectionPrinter that provides the functionality to print the collection
     */
    public InfoCommand(CollectionManager collectionManager, CollectionPrinter collectionPrinter) {
        super(collectionManager, collectionPrinter);
    }

    public InfoCommand() {}

    /**
     * Prints the information about the collection, including the type of the collection, the date of initialization,
     * and the number of elements in the collection.
     */
    @Override
    public void execute() {

        output = new StringBuilder();

        CollectionManager collectionManager = getCollectionManager();

        output.append(ConsoleColors.GREEN + "Collection type: " + ConsoleColors.RESET).append(collectionManager.getCollection().getClass()).append("\n");

        output.append(ConsoleColors.GREEN + "Date of initialization: " + ConsoleColors.RESET).append(collectionManager.getCreationDate()).append("\n");

        output.append(ConsoleColors.GREEN + "Number of elements in collection: " + ConsoleColors.RESET).append(collectionManager.getCollection().size()).append("\n");
    }

    @Override
    public Response getCommandResponse() {
        return new CommandResponse("info", getArgs(), output.toString());
    }
}
