package commonModule.commands.commandObjects;

import commonModule.dataStructures.network.CommandResponse;
import commonModule.exceptions.InvalidCoordinatesException;
import server.collectionManagement.CollectionManager;
import commonModule.collectionClasses.HumanBeing;
import commonModule.dataStructures.network.Response;
import commonModule.commands.CommandTemplate;
import commonModule.commands.CommandWithResponse;
import server.database.DatabaseManager;

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
    public void execute() throws SQLException, InvalidCoordinatesException {

        DatabaseManager databaseManager = getDatabaseManager();
        databaseManager.removeAll(getUserLogin());

        Map<Long, HumanBeing> data = getCollectionManager().getCollection();

        data.clear();
        data.putAll(getDatabaseManager().getDataLoader().loadCollection());
    }

    @Override
    public Response getCommandResponse() {
        return new CommandResponse("clear", getArgs(), "Done!");
    }
}
