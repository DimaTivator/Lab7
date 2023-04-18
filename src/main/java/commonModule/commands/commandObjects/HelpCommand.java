package commonModule.commands.commandObjects;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.commands.CommandTemplate;
import commonModule.dataStructures.network.CommandResponse;
import server.collectionManagement.CollectionPrinter;
import commonModule.dataStructures.network.Response;
import commonModule.commands.CommandWithResponse;

import java.util.Map;

/**
 * The HelpCommand class implements the behavior for the "help" command.
 * This command is used to display the available commands in the system and their descriptions.
 */
public class HelpCommand extends CommandTemplate implements CommandWithResponse {

    private StringBuilder output;

    /**
     Constructs a HelpCommand object.
     @param collectionPrinter the object that helps with printing the collection data
     */
    public HelpCommand(CollectionPrinter collectionPrinter) {
        super(collectionPrinter);
    }

    public HelpCommand() {}

    /**
     This method displays the available commands in the system and their descriptions.
     */
    @Override
    public void execute() {
        CollectionPrinter collectionPrinter = getCollectionPrinter();
        Map<String, String> commands = collectionPrinter.getCommands();

        output = new StringBuilder();

        commands.forEach((key, value) -> {

            output.append(ConsoleColors.GREEN);
            for (int i = 0; i < key.length(); i++) {
                char letter = key.charAt(i);

                if (letter == '<') {
                    output.append(letter).append(ConsoleColors.PURPLE);
                } else if (letter == '>') {
                    output.append(ConsoleColors.GREEN).append(letter);
                } else {
                    output.append(letter);
                }
            }
            output.append(ConsoleColors.RESET + ": ").append(value).append("\n");
        });
        output.append("\n");
    }

    @Override
    public Response getCommandResponse() {
        return new CommandResponse("help", getArgs(), output.toString());
    }
}
