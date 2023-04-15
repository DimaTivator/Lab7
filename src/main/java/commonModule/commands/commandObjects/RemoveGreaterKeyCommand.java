package commonModule.commands.commandObjects;

import server.collectionManagement.CollectionManager;
import commonModule.dataStructures.Response;
import commonModule.exceptions.EmptyCollectionException;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.collectionClasses.HumanBeing;
import commonModule.commands.CommandTemplate;
import commonModule.commands.CommandWithResponse;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The RemoveGreaterKeyCommand class extends the Command class.
 * This class is used to remove all key-value pairs in the collection where the key is greater than the input key.
 */
public class RemoveGreaterKeyCommand extends CommandTemplate implements CommandWithResponse {

    /**
     * Constructs a RemoveGreaterKeyCommand object with a CollectionManager object and an input key.
     *
     * @param collectionManager The CollectionManager object that the command operates on.
     */
    public RemoveGreaterKeyCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public RemoveGreaterKeyCommand() {}

    @Override
    public void setArgs(String[] args) throws InvalidArgumentsException {
        try {
            Long key = Long.parseLong(args[0]);
            super.setArgs(new String[]{ String.valueOf(key) });
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("The key must be a number! Please Try to enter a command again");
        }
    }

    /**
     * Removes all key-value pairs in the collection where the key is greater than the input key.
     * Throws an EmptyCollectionException if the collection is empty.
     *
     * @throws EmptyCollectionException If the collection is empty.
     */
    @Override
    public void execute() throws EmptyCollectionException {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        Long inputKey = Long.parseLong(getArgs()[0]);

        if (data.isEmpty()) {
            throw new EmptyCollectionException();
        }

        /*
         * A set to store the keys that are greater than the input key.
         */
        Set<Long> keySet = new HashSet<>();

        data.forEach((key, value) -> {
            if (key.compareTo(inputKey) > 0) {
                keySet.add(key);
            }
        });

        keySet.forEach(data::remove);

        getCollectionManager().sort();
    }

    @Override
    public Response getCommandResponse() {
        return new Response("remove_greater_key", getArgs(), "Done!");
    }
}

