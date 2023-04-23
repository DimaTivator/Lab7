package commonModule.io.consoleIO;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.commands.Command;
import commonModule.commands.commandObjects.*;
import commonModule.dataStructures.Pair;
import commonModule.dataStructures.Triplet;
import commonModule.exceptions.InvalidCoordinatesException;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.io.humanBeingInput.CarObjectConsoleReader;
import commonModule.io.humanBeingInput.CarObjectFileReader;
import commonModule.exceptions.InvalidInputException;
import commonModule.exceptions.commandExceptions.NoSuchCommandException;
import commonModule.io.humanBeingInput.HumanBeingObjectConsoleReader;
import commonModule.io.humanBeingInput.HumanBeingObjectFileReader;

import java.util.*;

/**
 * The {@code CommandParser} class is a console reader that is used to parse commands and arguments from the console or
 * a file.
 */
public class CommandParser extends ConsoleReader<Triplet<String, String[], Object>> {

    public CommandParser() {
        fillCommandLists();
    }

    /**
     * The {@code commandsList} ArrayList stores a list of supported commands.
     */
    private static final ArrayList<String> commandsList = new ArrayList<>() {{
        add("help");
        add("info");
        add("show");
        add("insert");
        add("update");
        add("remove_key");
        add("clear");
        add("execute_script");
        add("exit");
        add("remove_lower");
        add("replace_if_greater");
        add("remove_greater_key");
        add("count_less_than_impact_speed");
        add("filter_less_than_car");
        add("print_unique_mood");
    }};

    private static final ArrayList<String> humanBeingCommandsList = new ArrayList<>() {{
        add("insert");
        add("update");
        add("remove_lower");
        add("replace_if_greater");
    }};

    private final ArrayList<String> objectCommandsList = new ArrayList<>();

    private final ArrayList<String> lineArgCommands = new ArrayList<>();

    private final Map<String, Command> commandsTable = new HashMap<>();

    private void fillCommandLists() {

        ArrayList<String> humanBeingCommandsList = CommandParser.getHumanBeingCommandsList();
        objectCommandsList.addAll(humanBeingCommandsList);
        objectCommandsList.add("filter_less_than_car");

        lineArgCommands.add("remove_key");
        lineArgCommands.add("execute_script");
        lineArgCommands.add("remove_greater_key");
        lineArgCommands.add("count_less_than_impact_speed");
        lineArgCommands.add("insert");
        lineArgCommands.add("update");
        lineArgCommands.add("replace_if_greater");

        commandsTable.put("help", new HelpCommand());
        commandsTable.put("info", new InfoCommand());
        commandsTable.put("show", new ShowCommand());
        commandsTable.put("insert", new InsertCommand());
        commandsTable.put("update", new UpdateCommand());
        commandsTable.put("remove_key", new RemoveKeyCommand());
        commandsTable.put("clear", new ClearCollectionCommand());
        commandsTable.put("remove_lower", new RemoveLowerCommand());
        commandsTable.put("replace_if_greater", new ReplaceIfGreaterCommand());
        commandsTable.put("remove_greater_key", new RemoveGreaterKeyCommand());
        commandsTable.put("count_less_than_impact_speed", new CountLessThanImpactSpeedCommand());
        commandsTable.put("filter_less_than_car", new FilterLessThanCarCommand());
        commandsTable.put("print_unique_mood", new PrintUniqueMoodCommand());
    }

    public static ArrayList<String> getHumanBeingCommandsList() {
        return humanBeingCommandsList;
    }

    /**
     * Returns the {@code commandsList}.
     *
     * @return an ArrayList of supported commands
     */
    public static ArrayList<String> getCommandsList() {
        return commandsList;
    }


    /**
     * Reads a command and its arguments from the console and returns them as a Pair of command and arguments.
     *
     * @return a Pair of command and arguments
     * @throws NoSuchCommandException if the entered command is not found in the list of supported commands
     */
    @Override
    public Triplet<String, String[], Object> readObjectFromConsole() throws NoSuchCommandException, InvalidInputException {
        Scanner scanner = getConsoleScanner();
        System.out.print(ConsoleColors.BLUE_BRIGHT + "Enter a command: " + ConsoleColors.RESET);

        Pair<String, String[]> input = getCommand(scanner);
        String commandName = input.getFirst();
        String[] args = input.getSecond();
        Object object = null;

        if (humanBeingCommandsList.contains(commandName)) {
            HumanBeingObjectConsoleReader humanBeingObjectConsoleReader = new HumanBeingObjectConsoleReader();
            object = humanBeingObjectConsoleReader.readHumanBeingFromConsole();
        }

        if (commandName.equals("filter_less_than_car")) {
            CarObjectConsoleReader carObjectConsoleReader = new CarObjectConsoleReader();
            object = carObjectConsoleReader.readObjectFromConsole();
        }

        return new Triplet<>(commandName, args, object);
    }


    public Pair<String, String[]> getCommand(Scanner scanner) throws NoSuchCommandException {

        List<String> line = Arrays.stream(scanner.nextLine().strip().replaceAll(" +", " ").split(" ")).toList();

        if (!commandsList.contains(line.get(0)) || line.get(0).equals("")) {
            throw new NoSuchCommandException(String.format("Can't find command %s in commands list\n", line.get(0)) +
                    "Please try to enter command again\n");
        }

        String command = line.get(0);
        String[] args = line.subList(1, line.size()).toArray(new String[0]);

        return new Pair<>(command, args);
    }

    /**
     * Reads a command and its arguments from the file and returns them as a Pair of command and arguments.
     *
     * @return a Pair of command and arguments
     * @throws NoSuchCommandException if the entered command is not found in the list of supported commands
     */
    public Triplet<String, String[], Object> parseCommandFromFile(Scanner fileScanner) throws NoSuchCommandException, InvalidInputException, InvalidCoordinatesException {

        Pair<String, String[]> input = getCommand(fileScanner);
        String commandName = input.getFirst();
        String[] args = input.getSecond();
        Object object = null;

        if (humanBeingCommandsList.contains(commandName)) {
            HumanBeingObjectFileReader humanBeingObjectFileReader = new HumanBeingObjectFileReader(fileScanner);
            object = humanBeingObjectFileReader.readHumanBeingFromFile();
        }

        if (commandName.equals("filter_less_than_car")) {
            CarObjectFileReader carObjectFileReader = new CarObjectFileReader(fileScanner);
            object = carObjectFileReader.readData();
        }

        return new Triplet<>(commandName, args, object);
    }


    public Command pack(Triplet<String, String[], Object> parsedCommand) throws InvalidArgumentsException {

        String commandName = parsedCommand.getFirst();
        String[] args = parsedCommand.getSecond();
        Object object = parsedCommand.getThird();

        Command command = commandsTable.get(commandName);

        if (lineArgCommands.contains(commandName)) {
            if (args.length == 0) {
                throw new InvalidArgumentsException("Something wrong with command arguments :(\n" +
                        "Please check that you do not enter any arguments in the same line with the command");
            }
            command.setArgs(args);
        }
        if (objectCommandsList.contains(commandName)) {
            command.setValue(object);
        }

        return command;
    }
}
