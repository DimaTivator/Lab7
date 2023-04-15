package commonModule.commands.commandObjects;

import commonModule.commands.CommandTemplate;
import server.collectionManagement.CollectionManager;
import commonModule.dataStructures.Response;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.collectionClasses.HumanBeing;
import commonModule.commands.CommandWithResponse;

import java.util.Map;

/**
 * InsertCommand is a class that extends the Command class and provides an implementation for
 * inserting an element into the collection.
 */
public class InsertCommand extends CommandTemplate implements CommandWithResponse {

    /**
     * Constructor for the InsertCommand class
     *
     * @param collectionManager the CollectionManager instance to get the collection from
     */
    public InsertCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public InsertCommand() {}

    @Override
    public void setArgs(String[] args) throws InvalidArgumentsException {
        Long key;
        try {
            key = Long.parseLong(args[0]);
            super.setArgs(new String[]{ String.valueOf(key) });
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("The key must be a number! Please Try to enter a command again");
        }
    }

    /**
     * The method overrides the execute method from the Command class and provides an implementation
     * to insert the element into the collection.
     */
    @Override
    public void execute() throws InvalidArgumentsException {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        Long key = Long.parseLong(getArgs()[0]);

        if (data.containsKey(key)) {
            throw new InvalidArgumentsException("Collection already contains this key!\nPlease try to enter a command again");
        }

        HumanBeing value = (HumanBeing) getValue();
        data.put(key, value);
        value.updateId();

        getCollectionManager().sort();
    }

    @Override
    public Response getCommandResponse() {
        return new Response("insert", getArgs(), "Done!");
    }
}
