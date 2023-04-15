package commonModule.commands.commandObjects;

import server.collectionManagement.CollectionManager;
import commonModule.io.fileIO.out.HumanBeingXMLWriter;
import commonModule.commands.CommandTemplate;

/**
 * The SaveCollectionToFileCommand class extends the Command class.
 * This class is used to save the collection managed by CollectionManager to an XML file.
 */
public class SaveCollectionToFileCommand extends CommandTemplate {

    /**
     * Constructs a SaveCollectionToFileCommand object with a CollectionManager object, using the default file path.
     *
     * @param collectionManager The CollectionManager object that the command operates on.
     */
    public SaveCollectionToFileCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * Executes the SaveCollectionToFileCommand. This method uses the HumanBeingXMLWriter class to write the collection to an XML file.
     */
    @Override
    public void execute() {
        HumanBeingXMLWriter humanBeingXMLWriter = new HumanBeingXMLWriter();
        humanBeingXMLWriter.writeData(getCollectionManager().getCollection(), getArgs()[0]);
    }
}
