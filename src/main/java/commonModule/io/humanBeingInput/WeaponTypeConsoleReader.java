package commonModule.io.humanBeingInput;

import commonModule.io.consoleIO.ConsoleReader;
import commonModule.exceptions.InvalidInputException;
import commonModule.collectionClasses.WeaponType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The WeaponTypeConsoleReader class extends {@link ConsoleReader} and implements
 * the {@link #readObjectFromConsole()} method to read a {@link WeaponType} object
 */
public class WeaponTypeConsoleReader extends ConsoleReader<WeaponType> {

    /**
     * Reads a {@link WeaponType} object from the console.
     * Prints a list of available weapon types and prompts the user to
     * select one. The user's input is then converted to a {@link WeaponType} and returned.
     * @return a {@link WeaponType} object
     * @throws InvalidInputException if the user's input is not one of the available weapon types
     */
    @Override
    public WeaponType readObjectFromConsole() throws InvalidInputException {
        Scanner scanner = getConsoleScanner();

        System.out.println("Enter one of the following weapon types:");

        List<String> weaponTypeList = new ArrayList<>();
        Arrays.stream(WeaponType.values()).toList().forEach(mood -> weaponTypeList.add(mood.toString()));

        for (String weaponType : weaponTypeList) {
            System.out.println(weaponType);
        }

        String input = scanner.nextLine().toUpperCase().strip();

        if (!weaponTypeList.contains(input)) {
            throw new InvalidInputException("You should choose mood from the available weapon types!\nPlease try again\n");
        }

        return WeaponType.valueOf(input);
    }
}
