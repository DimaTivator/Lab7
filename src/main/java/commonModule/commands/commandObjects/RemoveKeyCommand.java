package commonModule.commands.commandObjects;

import server.collectionManagement.CollectionManager;
import commonModule.dataStructures.Response;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.collectionClasses.HumanBeing;
import commonModule.commands.CommandTemplate;
import commonModule.commands.CommandWithResponse;

import java.util.Map;

/**
 * The RemoveKeyCommand class extends the Command class.
 * This class is used to remove a key-value pair from the collection using a specified key.
 */
public class RemoveKeyCommand extends CommandTemplate implements CommandWithResponse {

    /**
     * Constructs a RemoveKeyCommand object with a CollectionManager object and a key.
     *
     * @param collectionManager The CollectionManager object that the command operates on.
     */
    public RemoveKeyCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public RemoveKeyCommand() {}

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
     * Removes a key-value pair from the collection using the specified key.
     */
    @Override
    public void execute() {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        data.remove(Long.parseLong(getArgs()[0]));

        getCollectionManager().sort();
    }

    @Override
    public Response getCommandResponse() {
        return new Response("remove_key", getArgs(), "Done!");
    }
}

