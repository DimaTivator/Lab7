package commonModule.io.humanBeingInput;

import commonModule.exceptions.InvalidInputException;
import commonModule.io.consoleIO.ConsoleReader;
import commonModule.collectionClasses.Car;

import java.util.Scanner;

/**
 * The CarObjectConsoleReader class extends the ConsoleReader class and implements the readObjectFromConsole method.
 * This class is used for reading {@link Car} objects from the console.
 */
public class CarObjectConsoleReader extends ConsoleReader<Car> {

    private final Car car = new Car();

    /**
     * Reads a Car object from the console by asking for its properties and creating a new Car object using the input.
     * @return a Car object created from the console input
     * @throws InvalidInputException if the input is invalid
     */
    @Override
    public Car readObjectFromConsole() throws InvalidInputException {
        Scanner scanner = getConsoleScanner();

        if (HumanBeingObjectConsoleReader.checkTheDesireToEnter("carName")) {
            String carName = HumanBeingObjectConsoleReader.readNameFromConsole();
            car.setName(carName);
        }

        if (HumanBeingObjectConsoleReader.checkTheDesireToEnter("cool")) {
            boolean cool = HumanBeingObjectConsoleReader.readBooleanFromConsole("cool");
            car.setCool(cool);
        }

        return car;
    }
}
