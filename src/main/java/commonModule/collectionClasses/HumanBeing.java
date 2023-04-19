package commonModule.collectionClasses;

import commonModule.auxiliaryClasses.ConsoleColors;

import java.io.Serializable;

/**
 * Class representing a human being.
 */
public class HumanBeing implements Comparable<HumanBeing>, Serializable {

    /*
    * Unique identifier for this HumanBeing object. Cannot be null.
    * Value must be greater than 0. Value is generated automatically.
    */
    private Long id;
    /**
     Name of the human being. Cannot be null. String value cannot be empty.
     */
    private String name;
    /**
     * Coordinates of the human being. Cannot be null.
     */
    private Coordinates coordinates;
    /**
     * Creation date of this HumanBeing object. Cannot be null.
     * Value is generated automatically.
     */
    private java.time.LocalDate creationDate;
    /**
     * Indicates whether this human being is a real hero.
     */
    private boolean realHero;
    /**
     * Indicates whether this human being has a toothpick.
     */
    private boolean hasToothpick;
    /**
     * Impact speed of this human being.
     */
    private double impactSpeed;
    /**
     * Weapon type of this human being.
     * Cannot be null.
     */
    private WeaponType weaponType;
    /**
     * Mood of this human being.
     * Cannot be null.
     */
    private Mood mood;
    /**
     * Car of this human being.
     * Can be null.
     */
    private Car car;

    /**
     * Keeps track of the last ID generated.
     */
    private static Long lastID = 0L;

    /**
     * Generates a unique ID for this HumanBeing object.
     * @return the generated ID
     */
    private Long generateId() {
        return ++lastID;
    }

    /**
     * Constructs a new HumanBeing object with the given name, mood, weapon type, and coordinates.
     *
     * @param name name of the human being
     * @param mood mood of the human being
     * @param weaponType weapon type of the human being
     * @param coordinates coordinates of the human being
     */
    public HumanBeing(String name, Mood mood, WeaponType weaponType, Coordinates coordinates) {
        this.name = name;
        this.mood = mood;
        this.weaponType = weaponType;
        this.coordinates = coordinates;

        creationDate = java.time.LocalDate.now();

        id = generateId();
    }

    public void setCreationDate(java.time.LocalDate date) {
        creationDate = date;
    }

    /**
     * Constructs a new HumanBeing object.
     */
    public HumanBeing() {
        creationDate = java.time.LocalDate.now();
        id = generateId();
    }

    public void setId(Long id) {
        this.id = id;
        lastID = id;
    }

    public void updateId() {
        id = ++HumanBeing.lastID;
    }


    /**
     * Returns the ID of this HumanBeing object.
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets name of the HumanBeing object
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this HumanBeing object.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the creation date of the HumanBeing object.
     * @return the creation date of the HumanBeing object.
     */
    public java.time.LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Returns the coordinates of the HumanBeing object.
     * @return the coordinates of the HumanBeing object.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the coordinates of the HumanBeing object.
     * @param coordinates the new coordinates to be set.
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Returns whether the HumanBeing is a real hero or not.
     * @return true if the HumanBeing is a real hero, false otherwise.
     */
    public boolean isRealHero() {
        return realHero;
    }

    /**
     * Sets whether the HumanBeing is a real hero or not.
     * @param realHero the value to set as the realHero attribute.
     */
    public void setRealHero(boolean realHero) {
        this.realHero = realHero;
    }

    /**
     * Returns the impact speed of the HumanBeing.
     * @return the impact speed of the HumanBeing.
     */
    public double getImpactSpeed() {
        return impactSpeed;
    }

    /**
     * Sets the impact speed of the HumanBeing.
     * @param impactSpeed the new impact speed to be set.
     */
    public void setImpactSpeed(double impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    /**
     * Returns whether the HumanBeing has a toothpick or not.
     * @return true if the HumanBeing has a toothpick, false otherwise.
     */
    public boolean isHasToothpick() {
        return hasToothpick;
    }

    /**
     * Indicates whether the {@link HumanBeing} has a toothpick.
     * @param hasToothpick A boolean value indicating if the {@link HumanBeing} has a toothpick.
     */
    public void setHasToothpick(boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    /**
     * Returns the type of weapon used by the {@link HumanBeing}.
     * @return The {@link WeaponType} used by the {@link HumanBeing}.
     */
    public WeaponType getWeaponType() {
        return weaponType;
    }

    /**
     * Sets the type of weapon used by the {@link HumanBeing}.
     * @param weaponType The {@link WeaponType} to be set for the {@link HumanBeing}.
     */
    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    /**
     * Returns the mood of the {@link HumanBeing}.
     * @return The {@link Mood} of the {@link HumanBeing}.
     */
    public Mood getMood() {
        return mood;
    }

    /**
     * Sets the mood of the {@link HumanBeing}.
     * @param mood The {@link Mood} to be set for the {@link HumanBeing}.
     */
    public void setMood(Mood mood) {
        this.mood = mood;
    }

    /**
     * Returns the car used by the {@link HumanBeing}.
     * @return The {@link Car} used by the {@link HumanBeing}.
     */
    public Car getCar() {
        return car;
    }

    /**
     * Sets the car used by the {@link HumanBeing}.
     * @param car The {@link Car} to be set for the {@link HumanBeing}.
     */
    public void setCar(Car car) {
        this.car = car;
    }



    /**
     * Compares the current {@link HumanBeing} instance to another {@link HumanBeing} object based on their IDs.
     * @param obj The {@link HumanBeing} object to be compared to the current {@link HumanBeing} instance.
     * @return An integer indicating the result of the comparison. A negative value if the current instance has a smaller ID,
     * zero if the IDs are equal, or a positive value if the current instance has a larger ID.
     */
    @Override
    public int compareTo(HumanBeing obj) {
        return id.compareTo(obj.getId());
    }

    private String fieldToString(String valueName, Object value) {
        return ConsoleColors.GREEN + valueName + ConsoleColors.RESET + ": " + value + "\n";
    }

    /**
     * Returns a string representation of the {@link HumanBeing} instance, including its field values.
     * @return A string representation of the {@link HumanBeing} instance.
     */
    @Override
    public String toString() {

        return ConsoleColors.GREEN + "HumanBeing object" + ConsoleColors.RESET + ":\n" +
                fieldToString("id", id) +
                fieldToString("name", name) +
                fieldToString("coordinates", coordinates) +
                fieldToString("creation date", creationDate) +
                fieldToString("mood", mood) +
                fieldToString("weapon type", weaponType) +
                fieldToString("realHero", realHero) +
                fieldToString("hasToothpick", hasToothpick) +
                fieldToString("impact speed", impactSpeed) +
                fieldToString("car", car);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj.getClass() != this.getClass()) return false;
        return id.equals(((HumanBeing) obj).getId());
    }
}
