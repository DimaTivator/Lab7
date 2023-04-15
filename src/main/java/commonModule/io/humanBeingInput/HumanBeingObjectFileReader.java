package commonModule.io.humanBeingInput;

import commonModule.collectionClasses.*;
import commonModule.exceptions.InvalidCoordinatesException;
import commonModule.io.fileIO.in.FileReader;
import commonModule.exceptions.InvalidInputException;

import java.util.Scanner;

/**
 * The `HumanBeingObjectFileReader` class extends `FileReader` and is responsible for reading data from a file
 * and creating a `HumanBeing` object from the data.
 */
public class HumanBeingObjectFileReader extends FileReader<HumanBeing> {

    private final Scanner fileScanner = getFileScanner();

    /**
     * Constructs a `HumanBeingObjectFileReader` with a `Scanner` object.
     *
     * @param fileScanner a `Scanner` object that will be used to read data from a file
     */
    public HumanBeingObjectFileReader(Scanner fileScanner) {
        super(fileScanner);
    }

    /**
     * Reads the name of a human being from a file.
     *
     * @return the name of a human being
     * @throws InvalidInputException if the name field is empty
     */
    private String readNameFromFile() throws InvalidInputException {

        String name = fileScanner.nextLine().strip();

        if (name.isEmpty()) {
            throw new InvalidInputException("Name field can't be empty!\nPlease try to enter name again\n");
        }

        return name;
    }

    /**
     * Reads a boolean value from a file.
     *
     * @param valueName the name of the value to be read
     * @return the boolean value read from the file
     * @throws InvalidInputException if the value read from the file is not "true" or "false"
     */
    private boolean readBooleanFromFile(String valueName) throws InvalidInputException {

        String input = fileScanner.nextLine().strip().toLowerCase();

        if (!(input.equals("true") || input.equals("false"))) {
            throw new InvalidInputException(
                    String.format("%s field must be true or false!\nPlease try to enter %s again\n", valueName, valueName)
            );
        }

        return Boolean.parseBoolean(input);
    }

    /**
     * Reads a double value from a file.
     *
     * @param valueName the name of the value to be read
     * @return the double value read from the file
     * @throws InvalidInputException if the value read from the file is not a valid double
     */
    private double readDoubleFromFile(String valueName) throws InvalidInputException {

        if (fileScanner.hasNextDouble()) {
            return Double.parseDouble(fileScanner.nextLine());
        }
        throw new InvalidInputException(
                String.format("%s field must be double!\nPlease try to enter %s again\n", valueName, valueName)
        );
    }

    /**
     * Reads data from a file and creates a `HumanBeing` object from the data.
     *
     * @return a `HumanBeing` object created from data read from a file
     */
    public HumanBeing readHumanBeingFromFile() throws InvalidInputException, InvalidCoordinatesException {

        HumanBeing humanBeing = new HumanBeing();

        String name;
//        try {
//            name = readNameFromFile();
//            humanBeing.setName(name);
//        } catch (InvalidInputException e) {
//            System.out.print(e.getMessage());
//        }
        name = readNameFromFile();
        humanBeing.setName(name);


        Coordinates coordinates;
        CoordinatesObjectFileReader coordinatesObjectFileReader = new CoordinatesObjectFileReader(fileScanner);
//        try {
//            coordinates = coordinatesObjectFileReader.readData();
//            humanBeing.setCoordinates(coordinates);
//        } catch (InvalidCoordinatesException | InvalidInputException e) {
//            System.out.print(e.getMessage());
//        }
        coordinates = coordinatesObjectFileReader.readData();
        humanBeing.setCoordinates(coordinates);


        WeaponType weaponType;
        WeaponTypeFileReader weaponTypeFileReader = new WeaponTypeFileReader(fileScanner);
//        try {
//            weaponType = weaponTypeFileReader.readData();
//            humanBeing.setWeaponType(weaponType);
//        } catch (InvalidInputException e) {
//            System.out.print(e.getMessage());
//        }
        weaponType = weaponTypeFileReader.readData();
        humanBeing.setWeaponType(weaponType);


        Mood mood;
        MoodFileReader moodFileReader = new MoodFileReader(fileScanner);
//        try {
//            mood = moodFileReader.readData();
//            humanBeing.setMood(mood);
//        } catch (InvalidInputException e) {
//            System.out.print(e.getMessage());
//        }
        mood = moodFileReader.readData();
        humanBeing.setMood(mood);


//        try {
//            boolean realHero = readBooleanFromFile("realHero");
//            humanBeing.setRealHero(realHero);
//        } catch (InvalidInputException e) {
//            System.out.print(e.getMessage());
//        }
        boolean realHero = readBooleanFromFile("realHero");
        humanBeing.setRealHero(realHero);



//        try {
//            boolean hasToothpick = readBooleanFromFile("hasToothpick");
//            humanBeing.setHasToothpick(hasToothpick);
//        } catch (InvalidInputException e) {
//            System.out.print(e.getMessage());
//        }
        boolean hasToothpick = readBooleanFromFile("hasToothpick");
        humanBeing.setHasToothpick(hasToothpick);


//        try {
//            double impactSpeed = readDoubleFromFile("impactSpeed");
//            humanBeing.setImpactSpeed(impactSpeed);
//        } catch (InvalidInputException e) {
//            System.out.print(e.getMessage());
//        }
        double impactSpeed = readDoubleFromFile("impactSpeed");
        humanBeing.setImpactSpeed(impactSpeed);


        Car car;
        CarObjectFileReader carObjectFileReader = new CarObjectFileReader(fileScanner);
        car = carObjectFileReader.readData();
        humanBeing.setCar(car);

        return humanBeing;
    }
}
