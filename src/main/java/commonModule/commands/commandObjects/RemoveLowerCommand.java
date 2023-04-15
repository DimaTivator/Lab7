package commonModule.commands.commandObjects;

import server.collectionManagement.CollectionManager;
import commonModule.dataStructures.Response;
import commonModule.exceptions.EmptyCollectionException;
import commonModule.collectionClasses.HumanBeing;
import commonModule.commands.CommandTemplate;
import commonModule.commands.CommandWithResponse;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The RemoveLowerCommand class extends the Command class.
 * This class is used to remove all key-value pairs in the collection where the value is less than the specified human being.
 */
public class RemoveLowerCommand extends CommandTemplate implements CommandWithResponse {

    /**
     * Constructs a RemoveLowerCommand object with a CollectionManager object and a human being.
     *
     * @param collectionManager The CollectionManager object that the command operates on.
     */
    public RemoveLowerCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public RemoveLowerCommand() {}

    /**
     * Removes all key-value pairs in the collection where the value is less than the specified human being.
     * Throws an EmptyCollectionException if the collection is empty.
     *
     * @throws EmptyCollectionException If the collection is empty.
     */
    @Override
    public void execute() throws EmptyCollectionException {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        HumanBeing humanBeing = (HumanBeing) getValue();

        if (data.isEmpty()) {
            throw new EmptyCollectionException();
        }

        data.entrySet().stream()
                .filter(entry -> entry.getValue().compareTo(humanBeing) < 0)
                .map(Map.Entry::getKey).toList()
                .forEach(data::remove);

        getCollectionManager().sort();
    }


    @Override
    public Response getCommandResponse() {
        return new Response("remove_lower", getArgs(), "Done!");
    }
}

