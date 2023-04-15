package commonModule.io.humanBeingInput;

import commonModule.exceptions.InvalidInputException;
import commonModule.io.consoleIO.ConsoleReader;
import commonModule.collectionClasses.Mood;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code MoodConsoleReader} class provides a method to read a {@link Mood} object from the console.
 */
public class MoodConsoleReader extends ConsoleReader<Mood> {

    /**
     * Reads a {@link Mood} object from the console by printing the available moods and asking the user to choose one.
     * If the user enters an invalid mood, an {@link InvalidInputException} is thrown.
     * @return the {@link Mood} object read from the console
     * @throws InvalidInputException if the user enters an invalid mood
     */
    @Override
    public Mood readObjectFromConsole() throws InvalidInputException {
        Scanner scanner = getConsoleScanner();

        System.out.println("Enter one of the following moods:");

        List<String> moodList = new ArrayList<>();
        Arrays.stream(Mood.values()).toList().forEach(mood -> moodList.add(mood.toString()));

        for (String mood : moodList) {
            System.out.println(mood);
        }

        String input = scanner.nextLine().toUpperCase().strip();

        if (!moodList.contains(input)) {
            throw new InvalidInputException("You should choose mood from the available moods!\nPlease try again\n");
        }

        return Mood.valueOf(input);
    }
}
