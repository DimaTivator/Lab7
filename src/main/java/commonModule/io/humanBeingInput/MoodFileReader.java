package commonModule.io.humanBeingInput;

import commonModule.exceptions.InvalidInputException;
import commonModule.io.fileIO.in.FileReader;
import commonModule.collectionClasses.Mood;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * MoodFileReader is a class that reads the input from a file and converts it into a {@link Mood} object.
 * The file should only contain a string representing one of the moods available in the {@link Mood} enum.
 */
public class MoodFileReader  extends FileReader<Mood> {

    /**
     * Creates a new instance of MoodFileReader with the specified file scanner.
     *
     * @param fileScanner The Scanner object used to read data from a file
     */
    public MoodFileReader(Scanner fileScanner) {
        super(fileScanner);
    }

    /**
     * Reads a string representing a mood from the file, converts it into a {@link Mood} object, and returns it.
     * If the string is not one of the available moods, an {@link InvalidInputException} is thrown.
     *
     * @return The Mood object representing the input read from the file
     * @throws InvalidInputException If the string read from the file is not one of the available moods
     */
    public Mood readData() throws InvalidInputException {
        Scanner fileScanner = getFileScanner();

        List<String> moodList = new ArrayList<>();
        Arrays.stream(Mood.values()).toList().forEach(mood -> moodList.add(mood.toString()));

        String mood = fileScanner.nextLine().toUpperCase().strip();

        if (!moodList.contains(mood)) {
            throw new InvalidInputException("You should choose mood from the available moods!\nPlease try again\n");
        }

        return Mood.valueOf(mood);
    }
}
