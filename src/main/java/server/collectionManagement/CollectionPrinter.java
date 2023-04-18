package server.collectionManagement;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 The CollectionPrinter class is responsible for printing information about the collection and its elements.
 It contains a map of available commands and their descriptions, and methods for printing collection information,
 unique mood values, elements with impact speed less than a specified value, elements with car less than a specified value,
 saving collection to file, and printing help information.
 */
public class CollectionPrinter {

    /**
     * A map of available commands and their descriptions.
     */
    private final Map<String, String> commands = new LinkedHashMap<>();

    /**
     * Fills the commands map with available commands and their descriptions.
     */
    private void setCommands() {
        commands.put("help", "print available commands");
        commands.put("info", "print information about the collection (type, initialization date, number of items, etc.)");
        commands.put("show", "print all items of the collection in (key - value) format");
        commands.put("insert <key> <value>", "add new pair (key, value) to collection");
        commands.put("update <id> <element>", "update the value of a collection item whose id is equal to the specified one");
        commands.put("remove_key <key>", "remove the item with the specified key");
        commands.put("clear", "clear collection");
        commands.put("execute_script <file_name>", "executes a script from the file with the specified name");
        commands.put("exit", "terminate the program (without saving to a file)");
        commands.put("remove_lower <element>", "remove all items less than the specified one from the collection");
        commands.put("replace_if_greater null <element>", "replace the value by key if the new value is greater than the old one");
        commands.put("remove_greater_key <key>", "remove from the collection all items whose key is greater than the specified one");
        commands.put("count_less_than_impact_speed <impactSpeed>", "print the number of elements whose impactSpeed field value is less than the specified one");
        commands.put("filter_less_than_car <car>", "print elements whose car field value is less than the specified one");
        commands.put("print_unique_mood", "print the unique values of the mood field of all items in the collection");
    }

    /**
     * Returns the map of available commands and their descriptions.
     *
     * @return the map of available commands and their descriptions
     */
    public Map<String, String> getCommands() {
        return commands;
    }

    /**
     * Constructor that calls setCommands method.
     */
    public CollectionPrinter() {
        setCommands();
    }
}
