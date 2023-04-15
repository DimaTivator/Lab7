package commonModule.io.fileIO.in;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import commonModule.auxiliaryClasses.ConsoleColors;
import commonModule.collectionClasses.*;
import commonModule.exceptions.InvalidCoordinatesException;
import commonModule.exceptions.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The class HumanBeingXMLParser extends the Parser class and reads data from a specified XML file
 * and parses it into a `LinkedHashMap` of HumanBeing objects.
 */
public class HumanBeingXMLParser extends Parser<LinkedHashMap<Long, HumanBeing>> {

    /**
     * Reads data from a specified XML file and returns a Document object.
     *
     * @param fileName the name of the XML file
     * @return a Document object containing the data from the XML file
     */
    public Document readData(String fileName) throws ParserConfigurationException, IOException, SAXException {

        // Read XML file and create a Document object
        File inputFile = new File(fileName);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(inputFile);
        document.getDocumentElement().normalize();

        return document;
    }

    /**
     * Parses the data from the specified XML file into a `LinkedHashMap` of HumanBeing objects.
     *
     * @param fileName the name of the XML file
     * @return a `LinkedHashMap` of HumanBeing objects containing the data from the XML file
     */
    public LinkedHashMap<Long, HumanBeing> parseData(String fileName) {

        // A LinkedHashMap to store the HumanBeing objects
        LinkedHashMap<Long, HumanBeing> humanBeings = new LinkedHashMap<>();

        try {
            // Read the data from the XML file and parse it into a LinkedHashMap

            Document document = readData(fileName);
            NodeList humanBeingList = document.getElementsByTagName("HumanBeings");

            for (int i = 0; i < humanBeingList.getLength(); i++) {

                Node humanBeingNode = humanBeingList.item(i);

                if (humanBeingNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element humanBeingElement = (Element) humanBeingNode;
                    NodeList humanBeingElements = humanBeingElement.getChildNodes();

                    for (int j = 0; j < humanBeingElements.getLength(); j++) {

                        Node humanBeing = humanBeingElements.item(j);

                        if (humanBeing.getNodeType() == Node.ELEMENT_NODE) {

                            Element eElement = (Element) humanBeing;

                            String name = parseName(eElement);
                            Coordinates coordinates = parseCoordinates((Element) eElement.getElementsByTagName("coordinates").item(i));
                            WeaponType weaponType = parseWeaponType(eElement);
                            Mood mood = parseMood(eElement);

                            HumanBeing obj = new HumanBeing(name, mood, weaponType, coordinates);

                            try {
                                java.time.LocalDate date = java.time.LocalDate.parse(eElement.getElementsByTagName("creationDate").item(0).getTextContent());
                                obj.setCreationDate(date);
                            } catch (Exception ignored) {}

                            try {
                                boolean realHero = Boolean.parseBoolean(eElement.getElementsByTagName("realHero").item(0).getTextContent());
                                obj.setRealHero(realHero);
                            } catch (Exception ignored) {}

                            try {
                                boolean hasToothpick = Boolean.parseBoolean(eElement.getElementsByTagName("hasToothpick").item(0).getTextContent());
                                obj.setHasToothpick(hasToothpick);
                            } catch (Exception ignored) {}

                            try {
                                double impactSpeed = Double.parseDouble(eElement.getElementsByTagName("impactSpeed").item(0).getTextContent());
                                obj.setImpactSpeed(impactSpeed);
                            } catch (Exception ignored) {}

                            try {
                                Car car = parseCar((Element) eElement.getElementsByTagName("car").item(i));
                                obj.setCar(car);
                            } catch (Exception ignored) {}

                            humanBeings.put(obj.getId(), obj);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED_BOLD + "Some troubles with xml file :(" + ConsoleColors.RESET);
            System.out.println(e.getMessage());
            return null;
        }

        return humanBeings;
    }

    /**
     * Parses the name from an XML element and returns it as a String.
     *
     * @param element the XML element containing the name
     * @return the name as a String
     * @throws ParseException if the name field is empty
     */
    private String parseName(Element element) throws ParseException {
        if (element.getElementsByTagName("name").item(0).getTextContent().isEmpty()) {
            throw new ParseException("Name field can't be empty!");
        }
        return element.getElementsByTagName("name").item(0).getTextContent();
    }

    /**
     * Parses the coordinates from an XML element and returns them as a Coordinates object.
     *
     * @param element the XML element containing the coordinates
     * @return the coordinates as a Coordinates object
     * @throws ParseException if there are troubles with parsing the coordinates
     * @throws InvalidCoordinatesException if the coordinates are invalid
     */
    private Coordinates parseCoordinates(Element element) throws ParseException, InvalidCoordinatesException {
        if (element.getElementsByTagName("x").getLength() != 1 || element.getElementsByTagName("y").getLength() != 1) {
            throw new ParseException("Some troubles with parsing coordinates :(");
        }

        Coordinates coordinates = new Coordinates();
        coordinates.setX(Double.parseDouble(element.getChildNodes().item(1).getTextContent()));
        coordinates.setY(Float.parseFloat(element.getChildNodes().item(3).getTextContent()));

        return coordinates;
    }

    /**
     * Parses the mood from an XML element and returns it as a Mood object.
     *
     * @param element the XML element containing the mood
     * @return the mood as a Mood object
     * @throws ParseException if there are troubles with parsing the mood
     */
    private Mood parseMood(Element element) throws ParseException {
        if (element.getElementsByTagName("mood").getLength() != 1) {
            throw new ParseException("Some troubles with parsing mood :(");
        }

        String tagContent = element.getElementsByTagName("mood").item(0).getTextContent();

        List<String> moodsList = new ArrayList<>();
        Arrays.stream(Mood.values()).forEach(mood -> moodsList.add(mood.toString()));

        if (!moodsList.contains(tagContent.toUpperCase())) {
            throw new ParseException("Can't find mood in list of available constants :(");
        }

        return Mood.valueOf(element.getElementsByTagName("mood").item(0).getTextContent().toUpperCase());
    }

    /**
     Parses the weapon type from the given XML element.
     *
     * @param element the XML element to parse
     * @return the weapon type
     * @throws ParseException if there are any troubles with parsing weapon type
     */
    private WeaponType parseWeaponType(Element element) throws ParseException {
        if (element.getElementsByTagName("weaponType").getLength() != 1) {
            throw new ParseException("Some troubles with parsing weapon type :(");
        }

        String tagContent = element.getElementsByTagName("weaponType").item(0).getTextContent();

        List<String> weaponTypesList = new ArrayList<>();
        Arrays.stream(WeaponType.values()).forEach(weaponType -> weaponTypesList.add(weaponType.toString()));

        if (!weaponTypesList.contains(tagContent.toUpperCase())) {
            throw new ParseException("Can't find weapon type in list of available constants :(");
        }

        return WeaponType.valueOf(element.getElementsByTagName("weaponType").item(0).getTextContent().toUpperCase());
    }

    /**
     * Parses the car from the given XML element.
     *
     * @param element the XML element to parse
     * @return the car
     * @throws ParseException if there are any troubles with parsing car
     */
    private Car parseCar(Element element) throws ParseException {
        if (element.getElementsByTagName("name").getLength() > 1 || element.getElementsByTagName("cool").getLength() > 1) {
            throw new ParseException("Some troubles with parsing car :(");
        }

        Car car = new Car();

        try {
            String carName;
            String carNameTagContent = element.getElementsByTagName("name").item(0).getTextContent();
            if (carNameTagContent.isEmpty()) {
                carName = "";
            } else {
                carName = carNameTagContent;
            }
            car.setName(carName);
        } catch (Exception ignored) {}

        boolean cool;
        try {
            String coolTagContent = element.getElementsByTagName("cool").item(0).getTextContent();
            cool = Boolean.parseBoolean(coolTagContent);
            car.setCool(cool);
        } catch (Exception ignored) {}

        return car;
    }
}