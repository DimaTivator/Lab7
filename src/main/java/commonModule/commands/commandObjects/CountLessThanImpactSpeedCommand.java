package commonModule.commands.commandObjects;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.dataStructures.network.CommandResponse;
import server.collectionManagement.CollectionManager;
import commonModule.dataStructures.network.Response;
import commonModule.exceptions.EmptyCollectionException;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.collectionClasses.HumanBeing;
import commonModule.commands.CommandTemplate;
import commonModule.commands.CommandWithResponse;

import java.util.Map;

/**
 * CountLessThanImpactSpeedCommand class is a concrete implementation of the Command abstract class.
 * It counts the number of elements in the collection whose `impactSpeed` is less than the specified `impactSpeed`.
 */
public class CountLessThanImpactSpeedCommand extends CommandTemplate implements CommandWithResponse {


    private StringBuilder output;

    /**
     * Constructs a CountLessThanImpactSpeedCommand with the specified CollectionManager and impact speed.
     *
     * @param collectionManager the CollectionManager to be used
     */
    public CountLessThanImpactSpeedCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public CountLessThanImpactSpeedCommand() {}

    @Override
    public void setArgs(String[] args) throws InvalidArgumentsException {
        try {
            double key = Double.parseDouble(args[0]);
            super.setArgs(new String[]{ String.valueOf(key) });
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("The key must be a number! Please Try to enter a command again");
        }
    }

    /**
     * Executes the command, counting the number of elements in the collection whose `impactSpeed` is less than the specified `impactSpeed`.
     *
     * @throws EmptyCollectionException if the collection is empty
     */
    @Override
    public void execute() throws EmptyCollectionException {

        output = new StringBuilder();

        CollectionManager collectionManager = getCollectionManager();
        Map<Long, HumanBeing> data = collectionManager.getCollection();
        double impactSpeed = Double.parseDouble(getArgs()[0]);

        if (data.isEmpty()) {
            throw new EmptyCollectionException();
        }

        int counter = 0;
        for (HumanBeing value : data.values()) {
            if (value.getImpactSpeed() < impactSpeed) {
                counter++;
            }
        }

        output.append("The number of objects whose impactSpeed field value is less than the specified one is " + ConsoleColors.GREEN);
        output.append(counter).append(ConsoleColors.RESET).append("\n");

    }

    @Override
    public Response getCommandResponse() {
        return new CommandResponse("count_less_than_impact_speed", getArgs(), output.toString());
    }
}
