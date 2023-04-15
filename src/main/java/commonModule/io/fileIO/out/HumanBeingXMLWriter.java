package commonModule.io.fileIO.out;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.collectionClasses.HumanBeing;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

/**
 * The class {@code HumanBeingXMLWriter} extends {@code FileWriter} and is responsible for writing a Map of {@code HumanBeing} objects to an XML file.
 * It writes data in XML format, where each {@code HumanBeing} is an XML element with its properties as nested elements.
 * The class has a constructor that takes a file name and a default constructor.
 * The method {@code writeData} writes the data to the XML file.
*/
public class HumanBeingXMLWriter extends FileWriter<Map<Long, HumanBeing>> {

    /**
     * Constructs a {@code HumanBeingXMLWriter} with a specified file name.
     * @param fileName the name of the XML file
     */
    public HumanBeingXMLWriter(String fileName) {
        super(fileName);
    }

    /**
     Default constructor
     */
    public HumanBeingXMLWriter() {};

    /**
     * Writes a Map of {@code HumanBeing} objects to an XML file.
     * @param data the Map of {@code HumanBeing} objects to be written to the file
     * @param filePath the path of the XML file
     */
    @Override
    public void writeData(Map<Long, HumanBeing> data, String filePath) {

        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(filePath))) {

            writer.write("<?xml version='1.0' encoding='UTF-8'?>\n");
            writer.write("<HumanBeings>\n");

            for (HumanBeing humanBeing : data.values()) {

                writer.write("\t<HumanBeing>\n");
                writer.write("\t\t<id>" + humanBeing.getId() + "</id>\n");
                writer.write("\t\t<name>" + humanBeing.getName() + "</name>\n");
                writer.write("\t\t<creationDate>" + humanBeing.getCreationDate() + "</creationDate>\n");
                writer.write("\t\t<realHero>" + humanBeing.isRealHero() + "</realHero>\n");
                writer.write("\t\t<hasToothpick>" + humanBeing.isHasToothpick() + "</hasToothpick>\n");
                writer.write("\t\t<impactSpeed>" + humanBeing.getImpactSpeed() + "</impactSpeed>\n");
                writer.write("\t\t<weaponType>" + humanBeing.getWeaponType() + "</weaponType>\n");
                writer.write("\t\t<mood>" + humanBeing.getMood() + "</mood>\n");
                writer.write("\t\t<coordinates>\n");
                writer.write("\t\t\t<x>" + humanBeing.getCoordinates().getX() + "</x>\n");
                writer.write("\t\t\t<y>" + humanBeing.getCoordinates().getY() + "</y>\n");
                writer.write("\t\t</coordinates>\n");

                if (humanBeing.getCar() != null) {
                    writer.write("\t\t<car>\n");

                    if (humanBeing.getCar().getName() != null) {
                        writer.write("\t\t\t<name>" + humanBeing.getCar().getName() + "</name>\n");
                    }

                    if (humanBeing.getCar().getCool() != null) {
                        writer.write("\t\t\t<cool>" + humanBeing.getCar().getCool() + "</cool>\n");
                    }

                    writer.write("\t\t</car>\n");
                }
                writer.write("\t</HumanBeing>\n");
            }
            writer.write("</HumanBeings>\n");
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED_BOLD + "Something wrong with saving data in XML file :(" + ConsoleColors.RESET);
            e.printStackTrace();
        }
    }
}
