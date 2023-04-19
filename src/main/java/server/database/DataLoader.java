package server.database;

import commonModule.collectionClasses.*;
import commonModule.exceptions.InvalidCoordinatesException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataLoader {

    private final DatabaseHandler databaseHandler;

    public DataLoader(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }


    public Map<Long, HumanBeing> loadCollection() throws SQLException, InvalidCoordinatesException {

        String query = "select h.\"humanBeing_pk\", h.name, h.x_coordinate, h.y_coordinate, h.real_hero, h.has_toothpick,\n" +
                "h.impact_speed, m.name, wt.name, h.car_id, car.name, car.cool, h.creation_date, h.collection_key\n" +
                "from \"HumanBeing\" h\n" +
                "inner join \"Mood\" m on h.mood_id = m.id\n" +
                "inner join \"Weapon_type\" wt on h.weapon_type_id = wt.id\n" +
                "left join \"Cars\" car on h.car_id = car.id;";

        Map<Long, HumanBeing> data = new LinkedHashMap<>();

        try (PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {


                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);

                Coordinates coordinates = new Coordinates();
                coordinates.setX(resultSet.getDouble(3));
                coordinates.setY(resultSet.getFloat(4));

                boolean real_hero = resultSet.getBoolean(5);
                boolean has_toothpick = resultSet.getBoolean(6);
                double impact_speed = resultSet.getDouble(7);

                Mood mood = Mood.valueOf(resultSet.getString(8));
                WeaponType weaponType = WeaponType.valueOf(resultSet.getString(9));

                Car car = null;
                int carId = resultSet.getInt(10);
                if (!resultSet.wasNull()) {
                    car = new Car();
                    car.setName(resultSet.getString(11));
                    car.setCool(resultSet.getBoolean(12));
                }

                LocalDate creationDate = LocalDate.parse(resultSet.getDate(13).toString());

                Long collection_key = resultSet.getLong(14);

                HumanBeing humanBeing = new HumanBeing();

                humanBeing.setId(id);
                humanBeing.setName(name);
                humanBeing.setCoordinates(coordinates);
                humanBeing.setRealHero(real_hero);
                humanBeing.setHasToothpick(has_toothpick);
                humanBeing.setImpactSpeed(impact_speed);
                humanBeing.setMood(mood);
                humanBeing.setWeaponType(weaponType);
                humanBeing.setCar(car);
                humanBeing.setCreationDate(creationDate);

                data.put(collection_key, humanBeing);
            }
        }

        return data;
    }


    public Map<Long, String> loadElementsOwners() throws SQLException {

        Map<Long, String> elementsOwners = new HashMap<>();

        String query = "select h.\"humanBeing_pk\", u.login from \"HumanBeing\" h inner join \"Users\" u on h.owner_id = u.id";

        try (PreparedStatement statement = databaseHandler.getConnection().prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long humanBeingPK = resultSet.getLong(1);
                String login = resultSet.getString(2);

                elementsOwners.put(humanBeingPK, login);
            }
        }

        return elementsOwners;
    }
}
