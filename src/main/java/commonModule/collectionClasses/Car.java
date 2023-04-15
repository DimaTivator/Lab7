package commonModule.collectionClasses;

import java.io.Serializable;

/**
 Class representing a Car object.
 */
public class Car implements Comparable<Car>, Serializable {

    /** The name of the car. Can be null. */
    private String name;

    /** The coolness of the car. Can be null. */
    private Boolean cool;

    public Car(String name, Boolean cool) {
        this.name = name;
        this.cool = cool;
    }

    public Car() {}

    /**
     * Gets the name of the car.
     * @return the name of the car
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the car.
     * @param name the new name of the car
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the coolness of the car.
     * @return the coolness of the car
     */
    public Boolean getCool() {
        return cool;
    }

    /**
     * Sets the coolness of the car.
     * @param cool the new coolness of the car
     */
    public void setCool(Boolean cool) {
        this.cool = cool;
    }

    @Override
    public int compareTo(Car obj) {
        if (obj.getName() == null) {
            return 1;
        }
        if (name == null) {
            return -1;
        }

        return name.compareTo(obj.getName());
    }

    /**
     * Returns a string representation of the Car object.
     * @return the string representation of the Car object
     */
    @Override
    public String toString() {
        return String.format("Object Car. Name: %s. Cool: %b", name, cool);
    }
}