package server.collectionManagement;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.collectionClasses.HumanBeing;
import commonModule.exceptions.EmptyCollectionException;
import commonModule.io.fileIO.out.HumanBeingXMLWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * CollectionManager is a class that stores a collection of {@link HumanBeing} objects.
 * The class is responsible for managing the collection, adding, updating, removing and replacing elements.
 * It also provides methods for removing elements by key, removing elements with a greater key, and clearing the collection.
 * The class is designed to use the command pattern to execute actions on the collection.
 */

public class CollectionManager {

    public static class Comparator implements java.util.Comparator<HumanBeing> {

        @Override
        public int compare(HumanBeing humanBeing1, HumanBeing humanBeing2) {
            return humanBeing1.getName().compareTo(humanBeing2.getName());
        }
    }

    /**
     * The creation date of the collection.
     */
    private final java.time.LocalDate creationDate;


    /**
     * The collection of {@link HumanBeing} objects.
     */
    private Map<Long, HumanBeing> data = Collections.synchronizedMap(new LinkedHashMap<>());

    private Map<Long, String> elementsOwners = Collections.synchronizedMap(new HashMap<>());

    /**
     * Constructs a new CollectionManager and sets the creation date to the current date.
     */
    public CollectionManager() {
        creationDate = java.time.LocalDate.now();
    }

    /**
     * Gets the creation date of the collection.
     *
     * @return the creation date of the collection.
     */
    public java.time.LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Gets the collection of {@link HumanBeing} objects.
     *
     * @return the collection of {@link HumanBeing} objects.
     */
    public Map<Long, HumanBeing> getCollection() {
        return data;
    }

    /**
     * Sets the collection of {@link HumanBeing} objects.
     *
     * @param collection the collection to set.
     */
    public void setCollection(Map<Long, HumanBeing> collection) throws EmptyCollectionException {
        if (collection == null) {
            throw new EmptyCollectionException();
        }
        data = collection;
    }


    public Map<Long, String> getElementsOwners() {
        return elementsOwners;
    }

    public void setElementsOwners(Map<Long, String> elementsOwners) {
        this.elementsOwners = elementsOwners;
    }

    public void save(String filePath) {

    }

    public void sort() {
        HashMap<Long, HumanBeing> sortedMap = new LinkedHashMap<>();

        data.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(new Comparator()))
                .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));

        data.clear();
        data.putAll(sortedMap);
    }
}
