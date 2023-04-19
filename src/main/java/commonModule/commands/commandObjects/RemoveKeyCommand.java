package commonModule.commands.commandObjects;

import commonModule.dataStructures.network.CommandResponse;
import commonModule.exceptions.ObjectAccessException;
import server.collectionManagement.CollectionManager;
import commonModule.dataStructures.network.Response;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.collectionClasses.HumanBeing;
import commonModule.commands.CommandTemplate;
import commonModule.commands.CommandWithResponse;
import server.database.DatabaseManager;

import java.sql.SQLException;
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
    public void execute() throws ObjectAccessException, SQLException {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        Long key = Long.parseLong(getArgs()[0]);

        if (!getCollectionManager().getElementsOwners().get(data.get(key).getId()).equals(getUserLogin())) {
            throw new ObjectAccessException();
        }

        DatabaseManager databaseManager = getDatabaseManager();
        databaseManager.removeHumanBeing(data.get(key).getId());

        data.remove(key);

        getCollectionManager().sort();
    }

    @Override
    public Response getCommandResponse() {
        return new CommandResponse("remove_key", getArgs(), "Done!");
    }
}

