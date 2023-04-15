package commonModule.commands.commandObjects;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.commands.CommandTemplate;
import commonModule.io.consoleIO.ConfirmationReader;

import java.io.File;

/**
 This class represents the "exit" command in the program
 */
public class ExitCommand extends CommandTemplate {

    private boolean collectionChanged;

    public void setCollectionChanged(boolean collectionChanged) {
        this.collectionChanged = collectionChanged;
    }

    /**
     Constructor for the {@code ExitCommand} class
     */
    public ExitCommand() {}

    /**
     This method overrides the execute method from the {@code Command} class
     Prompts the user for confirmation before exiting the program and terminating the process
     */
    @Override
    public void execute() {
        if (collectionChanged) {
            System.out.println("The program will end without saving the data");
            ConfirmationReader confirmationReader = new ConfirmationReader();
            String confirmation = confirmationReader.readObjectFromConsole();
            if (confirmation.equals("Y")) {
                System.out.println(ConsoleColors.PURPLE + "Bye!" + ConsoleColors.RESET);

                File saveInTmp = new File("/tmp/s367054Lab5Saves/.save.xml");
                boolean ignored = saveInTmp.delete();

                System.exit(0);
            }
        } else {
            System.out.println(ConsoleColors.PURPLE + "Bye!" + ConsoleColors.RESET);
            System.exit(0);
        }
    }
}
