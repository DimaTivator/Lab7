package commonModule.io.humanBeingInput;

import commonModule.io.consoleIO.ConsoleReader;
import commonModule.exceptions.InvalidCoordinatesException;
import commonModule.exceptions.InvalidInputException;
import commonModule.collectionClasses.Coordinates;

import java.util.Scanner;

/**
 * The {@code CoordinatesObjectConsoleReader} class extends {@code ConsoleReader} and is used to read a
 * {@code Coordinates} object from the console.
 */
public class CoordinatesObjectConsoleReader extends ConsoleReader<Coordinates> {

    private final Coordinates coordinates = new Coordinates();

    /**
     * Reads the {@code Coordinates} object from the console.
     * @return the {@code Coordinates} object read from the console.
     * @throws InvalidInputException if the input is invalid.
     * @throws InvalidCoordinatesException if the entered coordinates are invalid.
     */
    @Override
    public Coordinates readObjectFromConsole() throws InvalidInputException, InvalidCoordinatesException {
        Scanner scanner = getConsoleScanner();

        System.out.print("Coordinates (first x, then y): ");
        return getCoordinates(scanner, coordinates);
    }

    /**
     * Gets the {@code Coordinates} object from the {@code Scanner}.
     * @param scanner the {@code Scanner} to read the {@code Coordinates} object from.
     * @param coordinates the {@code Coordinates} object to be read.
     * @return the {@code Coordinates} object read from the {@code Scanner}.
     * @throws InvalidInputException if the input is invalid.
     * @throws InvalidCoordinatesException if the entered coordinates are invalid.
     */
    static Coordinates getCoordinates(Scanner scanner, Coordinates coordinates) throws InvalidInputException, InvalidCoordinatesException {
        String[] s = scanner.nextLine().strip().replaceAll(" +", " ").replaceAll(",", ".").split(" ");

        if (s.length != 2) {
            throw new InvalidInputException("Only 2 coordinates should be entered!\nPlease try again\n");
        }

        coordinates.setX(Double.parseDouble(s[0]));
        coordinates.setY(Float.parseFloat(s[1]));

        return coordinates;
    }
}
