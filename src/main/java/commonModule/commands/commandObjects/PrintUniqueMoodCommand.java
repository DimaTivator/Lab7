package commonModule.commands.commandObjects;

import commonModule.auxiliaryClasses.ConsoleColors;
import server.collectionManagement.CollectionManager;
import commonModule.dataStructures.Response;
import commonModule.exceptions.EmptyCollectionException;
import commonModule.collectionClasses.HumanBeing;
import commonModule.collectionClasses.Mood;
import commonModule.commands.CommandTemplate;
import commonModule.commands.CommandWithResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 PrintUniqueMoodCommand class is responsible for printing unique moods of the HumanBeing objects in the collection.
 */
public class PrintUniqueMoodCommand extends CommandTemplate implements CommandWithResponse {

    private StringBuilder output;

    /**
     Constructor for the PrintUniqueMoodCommand class.
     @param collectionManager instance of the CollectionManager class
     */
    public PrintUniqueMoodCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    public PrintUniqueMoodCommand() {}

    /**
     Overridden method from the Command class. This method is called when the "print_unique_mood" command is executed.
     @throws EmptyCollectionException if the collection is empty
     */
    @Override
    public void execute() throws EmptyCollectionException {

        output = new StringBuilder();

        Map<Long, HumanBeing> data = getCollectionManager().getCollection();

        if (data.isEmpty()) {
            throw new EmptyCollectionException();
        }

        Map<Mood, Integer> moodsCounter = new HashMap<>();

        data.forEach((key, value) -> {
            if (moodsCounter.containsKey(value.getMood())) {
                moodsCounter.put(value.getMood(), moodsCounter.get(value.getMood()) + 1);
            } else {
                moodsCounter.put(value.getMood(), 1);
            }
        });

        List<Mood> uniqueMoodList = new ArrayList<>();

        moodsCounter.forEach((key, value) -> {
            if (value == 1) {
                uniqueMoodList.add(key);
            }
        });

        if (uniqueMoodList.isEmpty()) {
            // System.out.println("There is no unique moods in collection :(");
            output.append("There is no unique moods in collection :(\n");
        } else {
            output.append(ConsoleColors.GREEN + "The list of unique moods of HumanBeing objects from the collection: " + ConsoleColors.RESET).append("\n");
            // System.out.println(ConsoleColors.GREEN + "The list of unique moods of HumanBeing objects from the collection: " + ConsoleColors.RESET);
            uniqueMoodList.forEach(System.out::println);
        }
    }

    @Override
    public Response getCommandResponse() {
        return new Response("print_unique_mood", getArgs(), output.toString());
    }
}
