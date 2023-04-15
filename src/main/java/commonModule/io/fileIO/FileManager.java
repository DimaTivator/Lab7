package commonModule.io.fileIO;

/**
 * The FileManager class is an abstract class that represents a basic implementation of reading and writing files.
 * It provides basic properties and methods that can be extended by concrete subclasses to implement specific file I/O operations.
 */
public abstract class FileManager {

    /**
     * The fileName field represents the name of the file being managed.
     */
    private String fileName;

    /**
     * The constructor for the FileManager class.
     * @param fileName The name of the file being managed.
     */
    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Default constructor
     */
    public FileManager() {}

    /**
     * The getFileName method returns the name of the file being managed.
     * @return The name of the file being managed.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * The setFileName method sets the name of the file being managed.
     * @param fileName The new name of the file being managed.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
