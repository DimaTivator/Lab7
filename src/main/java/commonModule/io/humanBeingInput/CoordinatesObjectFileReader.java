package commonModule.io.humanBeingInput;

import commonModule.io.fileIO.in.FileReader;
import commonModule.exceptions.InvalidCoordinatesException;
import commonModule.exceptions.InvalidInputException;
import commonModule.collectionClasses.Coordinates;

import java.util.Scanner;

/**
 * CoordinatesObjectFileReader is a subclass of {@link FileReader} for reading Coordinates data from a file.
 * This class takes a {@link Scanner} object to read the data. It implements the abstract method
 * {@link FileReader readData()}.
 * The method reads the data from the file, sets it to the coordinates object and returns it.
 */
public class CoordinatesObjectFileReader extends FileReader<Coordinates> {

    private final Coordinates coordinates = new Coordinates();

    /**
     * Constructs a CoordinatesObjectFileReader object with a Scanner object to read the data from.
     *
     * @param fileScanner a Scanner object for reading the data from a file
     */
    public CoordinatesObjectFileReader(Scanner fileScanner) {
        super(fileScanner);
    }

    /**
     * Reads the data from the file and sets it to the coordinates object.
     *
     * @return the coordinates object with data read from the file
     * @throws InvalidInputException when invalid input is entered
     * @throws InvalidCoordinatesException when coordinates are invalid
     */
    public Coordinates readData() throws InvalidInputException, InvalidCoordinatesException {
        Scanner scanner = getFileScanner();

        return CoordinatesObjectConsoleReader.getCoordinates(scanner, coordinates);
    }
}
