package commonModule.commands;

import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import server.collectionManagement.CollectionManager;
import server.collectionManagement.CollectionPrinter;

public interface Command {

    void execute() throws Exception;

    String[] getArgs();

    void setArgs(String[] args) throws InvalidArgumentsException;

    Object getValue();

    void setValue(Object value);

    void setCollectionManager(CollectionManager collectionManager);

    void setCollectionPrinter(CollectionPrinter collectionPrinter);
}
