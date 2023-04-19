package commonModule.commands.commandObjects;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.commands.Command;
import commonModule.commands.CommandTemplate;
import commonModule.dataStructures.network.CommandResponse;
import commonModule.exceptions.ObjectAccessException;
import server.collectionManagement.CollectionManager;
import commonModule.dataStructures.network.Response;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.collectionClasses.HumanBeing;
import commonModule.commands.CommandWithResponse;
import server.database.DatabaseManager;

import java.sql.SQLException;
import java.util.Map;

/**
 The UpdateCommand class updates the value of an existing key in the collection.
 */
public class UpdateCommand extends CommandTemplate implements CommandWithResponse {

    /**
     * Constructs a new UpdateCommand instance.
     * @param collectionManager The CollectionManager to execute the command on.
     */
    public UpdateCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public UpdateCommand() {}

    @Override
    public void setArgs(String[] args) throws InvalidArgumentsException {
        Long id;
        try {
            id = Long.parseLong(args[0]);
            super.setArgs(new String[]{ String.valueOf(id) });
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("The key must be a number! Please Try to enter a command again");
        }
    }

    /**
     * Executes the UpdateCommand. This method updates the value of an existing key in the collection.
     */
    @Override
    public void execute() throws InvalidArgumentsException, ObjectAccessException, SQLException {
        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        Long id = Long.parseLong(getArgs()[0]);
        HumanBeing newValue = (HumanBeing) getValue();

        boolean containsId = data.values().stream().anyMatch(humanBeing -> humanBeing.getId().equals(id));
        if (!containsId) {
            throw new InvalidArgumentsException(ConsoleColors.RED +
                    "Can't find the entered id in collection:(\nPlease try to enter the command again" + ConsoleColors.RESET);
        }

        if (!getCollectionManager().getElementsOwners().get(id).equals(getUserLogin())) {
            throw new ObjectAccessException();
        }

        DatabaseManager databaseManager = getDatabaseManager();

        for (Long key : data.keySet()) {
            HumanBeing value = data.get(key);

            if (id.equals(value.getId())) {
                databaseManager.removeHumanBeing(value.getId());
                databaseManager.insertHumanBeing(newValue, getUserLogin(), key);
            }
        }

        data.entrySet().stream()
                .filter(entry -> id.equals(entry.getValue().getId()))
                .findFirst()
                .ifPresent(entry -> {
                    newValue.setId(entry.getValue().getId());
                    data.put(entry.getKey(), newValue);
                });

        getCollectionManager().sort();
    }


    @Override
    public Response getCommandResponse() {
        return new CommandResponse("update", getArgs(), "Done!");
    }
}
