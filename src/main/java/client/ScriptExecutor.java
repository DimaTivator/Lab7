package client;


import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.dataStructures.network.CommandRequest;
import commonModule.dataStructures.network.CommandResponse;
import commonModule.dataStructures.network.Request;
import commonModule.dataStructures.Triplet;
import commonModule.exceptions.InvalidCoordinatesException;
import commonModule.exceptions.ScriptsRecursionException;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.exceptions.commandExceptions.NoSuchCommandException;
import commonModule.io.consoleIO.CommandParser;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * The {@code ScriptExecutor} class provides the functionality to execute commands from a script file.
 */
public class ScriptExecutor {

    private Set<String> usedScripts = new HashSet<>();

    public ScriptExecutor() {}

    /**
     * Executes the commands from the script file located at the given file path.
     */
    public void executeScript(String filePath, NetworkProvider networkProvider, Authenticator authenticator) throws Exception {

        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        CommandParser commandParser = new CommandParser();
        CommandRequest request;

        usedScripts.add(filePath);

        while (true) {

            try {
                Triplet<String, String[], Object> parsedCommand = commandParser.parseCommandFromFile(scanner);
                String commandName = parsedCommand.getFirst();
                String[] args = parsedCommand.getSecond();

                if (commandName.equals("execute_script")) {
                    if (usedScripts.contains(args[0])) {
                        throw new ScriptsRecursionException("You should not call execute_script recursively!");
                    }
                    usedScripts.add(args[0]);
                    executeScript(args[0], networkProvider, authenticator);
                }
                else {
                    request = new CommandRequest(commandParser.pack(parsedCommand));
                    request.setLogin(authenticator.getLogin());
                    request.setPassword(authenticator.getPassword());
                    networkProvider.send(request);

                    CommandResponse response = (CommandResponse) networkProvider.receive();

                    if (response == null) {
                        System.out.println(ConsoleColors.RED + "Server is down :(\nPlease try again later" + ConsoleColors.RESET);
                    } else {
                        System.out.println(response.getOutput());
                    }
                }

            } catch (ScriptsRecursionException | NoSuchCommandException | InvalidArgumentsException |
                     InvalidCoordinatesException e) {
                System.out.println(e.getMessage());

            } catch (Exception e) {
                break;
            }
        }
        usedScripts.clear();
    }
}

