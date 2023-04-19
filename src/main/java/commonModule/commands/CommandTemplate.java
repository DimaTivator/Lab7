package commonModule.commands;

import server.collectionManagement.CollectionManager;
import server.collectionManagement.CollectionPrinter;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import server.database.DatabaseHandler;
import server.database.DatabaseManager;

import java.io.Serializable;
import java.sql.DriverManager;

/**
 * The Command class is an abstract class that serves as a blueprint for various command objects
 * used in the program. Each command object represents an action that can be performed on the
 * collection, such as adding or removing elements, printing elements, and so on.
 */
public abstract class CommandTemplate implements Command, Serializable {

    /**
     * The CollectionManager object that will be used to perform operations on the collection.
     */
    private CollectionManager collectionManager;

    /**
     * The CollectionPrinter object that will be used to print elements in the collection.
     */
    private CollectionPrinter collectionPrinter;

    private DatabaseManager databaseManager;

    private DatabaseHandler databaseHandler;

    private String userLogin;

    /**
     * Constructor to initialize the Command object with both the CollectionManager and CollectionPrinter objects.
     *
     * @param collectionManager the CollectionManager object to be used
     * @param collectionPrinter the CollectionPrinter object to be used
     */
    public CommandTemplate(CollectionManager collectionManager, CollectionPrinter collectionPrinter) {
        this.collectionManager = collectionManager;
        this.collectionPrinter = collectionPrinter;
    }

    /**
     * Constructor to initialize the Command object with only the CollectionManager object.
     *
     * @param collectionManager the CollectionManager object to be used
     */
    public CommandTemplate(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Constructor to initialize the Command object with only the CollectionPrinter object.
     *
     * @param collectionPrinter the CollectionPrinter object to be used
     */

    public CommandTemplate(CollectionPrinter collectionPrinter) {
        this.collectionPrinter = collectionPrinter;
    }

    /**
     * Default constructor.
     */
    public CommandTemplate() {}

    /**
     * Gets the CollectionManager object used in the Command.
     *
     * @return the CollectionManager object used
     */
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    /**
     * Gets the CollectionPrinter object used in the Command.
     *
     * @return the CollectionPrinter object used
     */
    public CollectionPrinter getCollectionPrinter() {
        return collectionPrinter;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void setCollectionPrinter(CollectionPrinter collectionPrinter) {
        this.collectionPrinter = collectionPrinter;
    }


    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }


    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    private String[] args;

    private Object value;

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) throws InvalidArgumentsException {
        this.args = args;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Abstract method that must be implemented by concrete subclasses.
     * This method specifies the action that the command will perform on the collection.
     *
     * @throws Exception if any error occurs while executing the command
     */
    public abstract void execute() throws Exception;
}
