package commonModule.io.fileIO.in;

import org.w3c.dom.Document;

/**
 * This is the abstract class Parser, it extends from FileReader and implements a method for parsing data from the input file into a data structure of type T.
 * @param <T> The type of the data structure that the parsed data will be stored in.
 */
public abstract class Parser<T> extends FileReader<Document> {

    /**
     * Constructor for the Parser class that takes a file name as a parameter.
     *
     * @param fileName The name of the input file to be parsed.
     */
    public Parser(String fileName) {
        super(fileName);
    }

    /**
     * No-arg constructor for the Parser class.
     */
    public Parser() {};

    /**
     * Abstract method that should be implemented by subclasses to parse the data from the input file into a data structure of type T.
     *
     * @param fileName The name of the input file to be parsed.
     * @return The data structure containing the parsed data.
     */
    public abstract T parseData(String fileName);
}
