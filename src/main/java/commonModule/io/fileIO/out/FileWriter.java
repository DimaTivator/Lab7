package commonModule.io.fileIO.out;

import commonModule.io.fileIO.FileManager;

/**
 * Abstract class that provides basic functionality for writing data to a file.
 * @param <T> Type of the data that needs to be written to the file.
 */
public abstract class FileWriter<T> extends FileManager {

    /**
    * Constructor that takes in the file name as a parameter.
    * @param fileName The name of the file to write data to.
    */
    public FileWriter(String fileName) {
        super(fileName);
    }

    /**
     * Default constructor.
     */
    public FileWriter() {};

    /**
     * Abstract method that writes data to a file.
     * @param data The data to be written to the file.
     * @param fileName The name of the file to write data to.
     */
    public abstract void writeData(T data, String fileName);
}
