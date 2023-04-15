package commonModule.io.humanBeingInput;

import commonModule.exceptions.InvalidInputException;
import commonModule.io.fileIO.in.FileReader;
import commonModule.collectionClasses.WeaponType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The WeaponTypeFileReader class extends the {@link FileReader} class and reads WeaponType objects from a file.
 * It implements the {@link #readData()} method to read a single line from the file and convert it to a WeaponType enum.
 */
public class WeaponTypeFileReader extends FileReader<WeaponType> {

    /**
     * Constructs a new WeaponTypeFileReader object.
     * @param fileScanner The file scanner to read the data from.
     */
    public WeaponTypeFileReader(Scanner fileScanner) {
        super(fileScanner);
    }

    /**
     * Reads a single line from the file and converts it to a WeaponType enum.
     * @return the WeaponType enum value that was read from the file.
     * @throws InvalidInputException if the line doesn't match any of the available WeaponType enum values.
     */
    public WeaponType readData() throws InvalidInputException {
        Scanner scanner = getFileScanner();

        List<String> weaponTypeList = new ArrayList<>();
        Arrays.stream(WeaponType.values()).toList().forEach(mood -> weaponTypeList.add(mood.toString()));

        String weaponType = scanner.nextLine().toUpperCase().strip();

        if (!weaponTypeList.contains(weaponType)) {
            throw new InvalidInputException("You should choose mood from the available weapon types!\nPlease try again\n");
        }

        return WeaponType.valueOf(weaponType);
    }
}
