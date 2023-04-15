package commonModule.io.fileIO.in;

import commonModule.io.fileIO.FileManager;

import java.util.Scanner;

/**
 * An abstract class that provides common functionality for reading from a file.
 * It extends {@link FileManager} and provides a {@link Scanner} object for reading from a file.
 */
public abstract class FileReader<T> extends FileManager {

    /**
     * A scanner object for reading from a file.
     */
    private Scanner fileScanner;

    /**
     * Constructs a FileReader with a specified Scanner object.
     *
     * @param fileScanner the Scanner object used to read from a file
     */
    public FileReader(Scanner fileScanner) {
        this.fileScanner = fileScanner;
    }

    /**
     * Constructs a FileReader with a specified file name.
     *
     * @param fileName the name of the file to be read
     */
    public FileReader(String fileName) {
        super(fileName);
    }

    /**
     * Constructs a FileReader without specifying the file name.
     */
    public FileReader() {}

    /**
     * Gets the Scanner object for reading from a file.
     *
     * @return the Scanner object for reading from a file
     */
    public Scanner getFileScanner() {
        return fileScanner;
    }
}
