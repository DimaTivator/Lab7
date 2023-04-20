package commonModule.commands.commandObjects;

import commonModule.commands.CommandTemplate;
import commonModule.dataStructures.network.CommandResponse;
import server.collectionManagement.CollectionManager;
import commonModule.dataStructures.network.Response;
import commonModule.exceptions.EmptyCollectionException;
import commonModule.collectionClasses.HumanBeing;
import commonModule.commands.CommandWithResponse;

import java.util.Map;

/**
 * Class representing the ShowCommand, which extends the Command class.
 */
public class ShowCommand extends CommandTemplate implements CommandWithResponse {

    private StringBuilder output;

    /**
     * Constructor that takes in a CollectionManager object.
     * @param collectionManager the CollectionManager object.
     */
    public ShowCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public ShowCommand() {}

    /**
     * Overrides the execute method to show the data of the collection.
     * Throws an EmptyCollectionException if the collection is empty.
     * @throws EmptyCollectionException if the collection is empty.
     */
    @Override
    public void execute() throws EmptyCollectionException {

        output = new StringBuilder();

        Map<Long, HumanBeing> data = getCollectionManager().getCollection();

        if (data.isEmpty()) {
            throw new EmptyCollectionException();
        }

        data.forEach((key, value) -> output.append(key).append(":\n").append(value).append("\n"));
        // System.out.println(output.toString());
    }

    @Override
    public Response getCommandResponse() {
        return new CommandResponse("show", getArgs(), output.toString());
    }
}
