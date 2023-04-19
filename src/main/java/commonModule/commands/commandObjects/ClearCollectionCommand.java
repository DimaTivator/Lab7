package commonModule.commands.commandObjects;

import commonModule.dataStructures.network.CommandResponse;
import server.collectionManagement.CollectionManager;
import commonModule.collectionClasses.HumanBeing;
import commonModule.dataStructures.network.Response;
import commonModule.commands.CommandTemplate;
import commonModule.commands.CommandWithResponse;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The class clears the collection.
 * The class uses the CollectionManager to access the data.
 * The class extends the abstract class "Command"
 */
public class ClearCollectionCommand extends CommandTemplate implements CommandWithResponse {

    public ClearCollectionCommand() {}

    /**
     Constructor for ClearCollectionCommand class.
     @param collectionManager the manager of the collection.
     */
    public ClearCollectionCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * Method that clears the collection.
     */
    @Override
    public void execute() throws SQLException {

        Map<Long, HumanBeing> data = getCollectionManager().getCollection();

        Set<Long> keySet = new HashSet<>();

        Map <Long, String> elementsOwners = getCollectionManager().getElementsOwners();

        for (Map.Entry<Long, HumanBeing> entry : data.entrySet()) {
            Long key = entry.getKey();
            HumanBeing value = entry.getValue();
            if (elementsOwners.get(value.getId()).equals(getUserLogin()) && getDatabaseManager().removeHumanBeing(value.getId())) {
                keySet.add(key);
            }
        }

        keySet.forEach(data::remove);
        keySet.forEach(elementsOwners::remove);
    }

    @Override
    public Response getCommandResponse() {
        return new CommandResponse("clear", getArgs(), "Done!");
    }
}
