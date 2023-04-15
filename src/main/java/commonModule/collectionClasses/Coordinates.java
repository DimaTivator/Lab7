package commonModule.collectionClasses;

import commonModule.exceptions.InvalidCoordinatesException;

import java.io.Serializable;

/**
 * The Coordinates class represents the coordinates of a location with an x and y value.
 * The x value has a maximum value of 487 and the y value should be greater than -703.
 * The y value cannot be null.
 */
public class Coordinates implements Comparable<Coordinates>, Serializable {
    private double x;
    private Float y;

    /**
     * Constructs a new Coordinates object with the specified x and y values.
     * @param x the x value of the coordinate.
     * @param y the y value of the coordinate.
     * @throws InvalidCoordinatesException if x is greater than 487 or y is less than or equal to -703.
     */
    public Coordinates(double x, Float y) throws InvalidCoordinatesException {
        if (x > 487 || y <= -703) {
            throw new InvalidCoordinatesException();
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new Coordinates object with default x and y values.
     */
    public Coordinates() {}

    /**
     * Returns the x value of the coordinate.
     * @return the x value of the coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x value of the coordinate.
     * @param x the x value to set.
     * @throws InvalidCoordinatesException if x is greater than 487.
     */
    public void setX(double x) throws InvalidCoordinatesException {
        if (x > 487) {
            throw new InvalidCoordinatesException();
        }
        this.x = x;
    }

    /**
     * Returns the y value of the coordinate.
     * @return the y value of the coordinate.
     */
    public Float getY() {
        return y;
    }

    /**
     * Sets the y value of the coordinate.
     * @param y the y value to set.
     * @throws InvalidCoordinatesException if y is less than or equal to -703.
     */
    public void setY(Float y) throws InvalidCoordinatesException {
        if (y <= -703) {
            throw new InvalidCoordinatesException();
        }
        this.y = y;
    }

    /**
     * Compares this Coordinates object to another Coordinates object.
     * @param obj the Coordinates object to compare with.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Coordinates obj) {
        int result = Double.compare(this.x, obj.x);
        if (result == 0) {
            result = this.y.compareTo(obj.y);
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }
}