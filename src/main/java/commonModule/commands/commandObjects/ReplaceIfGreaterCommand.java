package commonModule.commands.commandObjects;

import server.collectionManagement.CollectionManager;
import commonModule.dataStructures.Response;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.collectionClasses.HumanBeing;
import commonModule.commands.CommandTemplate;
import commonModule.commands.CommandWithResponse;

import java.util.Map;

/**
 * The ReplaceIfGreaterCommand class extends the Command class.
 * This class is used to replace the value in the collection associated with a specified key with the specified value
 * if the value is greater than the current value.
 */
public class ReplaceIfGreaterCommand extends CommandTemplate implements CommandWithResponse {

    /**
     * Constructs a ReplaceIfGreaterCommand object with a CollectionManager object, a key, and a value.
     *
     * @param collectionManager The CollectionManager object that the command operates on.
     */
    public ReplaceIfGreaterCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public ReplaceIfGreaterCommand() {}

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
     * Replaces the value in the collection associated with the specified key with the specified value
     * if the value is greater than the current value.
     */
    @Override
    public void execute() {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        Long key = Long.parseLong(getArgs()[0]);
        HumanBeing value = (HumanBeing) getValue();

        if (value.compareTo(data.get(key)) > 0) {
            value.updateId();
            data.put(key, value);
        }

        getCollectionManager().sort();
    }

    @Override
    public Response getCommandResponse() {
        return new Response("replace_if_greater", getArgs(), "Done!");
    }
}

