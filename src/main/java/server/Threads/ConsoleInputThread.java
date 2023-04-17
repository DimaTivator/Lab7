package server.Threads;

import commonModule.dataStructures.Pair;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import commonModule.exceptions.commandExceptions.NoSuchCommandException;
import org.slf4j.Logger;
import server.ServerCommandExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleInputThread extends Thread {
    public ConsoleInputThread(ServerCommandExecutor serverCommandExecutor, Logger logger) {
        super(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                List<String> line = Arrays.stream(scanner.nextLine().strip().replaceAll(" +", " ").split(" ")).toList();
                Pair<String, String[]> commandPair = new Pair<>(line.get(0), line.subList(1, line.size()).toArray(new String[0]));
                logger.info("Server command `{}` successfully read", commandPair.getFirst());
                try {
                    serverCommandExecutor.execute(commandPair);
                } catch (InvalidArgumentsException | NoSuchCommandException e) {
                    logger.error(e.getMessage());
                }
            }
        });
    }
}
