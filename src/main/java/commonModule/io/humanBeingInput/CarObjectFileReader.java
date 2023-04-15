package commonModule.io.humanBeingInput;

import commonModule.io.fileIO.in.FileReader;
import commonModule.collectionClasses.Car;

import java.util.Scanner;

/**
 * The {@code CarObjectFileReader} class extends the {@link FileReader} class and is used to read data about a {@link Car}
 * object from a file using a {@link Scanner}.
 */
public class CarObjectFileReader extends FileReader<Car> {

    private final Car car = new Car();

    /**
     * Constructs a {@code CarObjectFileReader} object with a {@link Scanner} object to read from a file.
     *
     * @param fileScanner the {@link Scanner} object to read from a file
     */
    public CarObjectFileReader(Scanner fileScanner) {
        super(fileScanner);
    }

    /**
     * Reads data about a {@link Car} object from a file using a {@link Scanner}.
     *
     * @return a {@link Car} object created from the data read from the file
     */
    public Car readData() {
        Scanner fileScanner = getFileScanner();

        String carName = fileScanner.nextLine().strip();
        car.setName(carName);

        boolean cool = Boolean.parseBoolean(fileScanner.nextLine().strip());
        car.setCool(cool);

        return car;
    }
}
