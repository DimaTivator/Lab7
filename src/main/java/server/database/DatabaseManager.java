package server.database;

import commonModule.collectionClasses.Car;
import commonModule.collectionClasses.HumanBeing;
import commonModule.collectionClasses.Mood;
import commonModule.collectionClasses.WeaponType;
import server.collectionManagement.CollectionManager;

import java.sql.*;

public class DatabaseManager extends CollectionManager {

    private final DatabaseHandler databaseHandler;
    private final DataLoader dataLoader;

    public DatabaseManager(DatabaseHandler databaseHandler, DataLoader dataLoader) {
        this.databaseHandler = databaseHandler;
        this.dataLoader = dataLoader;
    }

    public DataLoader getDataLoader() {
        return dataLoader;
    }


    public boolean insertCar(Car car) throws SQLException {

        String carInsertQuery = "insert into \"Cars\" (name, cool) values (?, ?)";

        try (PreparedStatement carInsertStatement = databaseHandler.getConnection().prepareStatement(carInsertQuery)) {

            carInsertStatement.setString(1, car.getName());
            carInsertStatement.setBoolean(2, car.getCool());

            carInsertStatement.executeUpdate();

            return true;
        }
    }


    public int getMoodId(Mood mood) throws SQLException {

        String getMoodQuery = "select id from \"Mood\" where name = ?";

        try (PreparedStatement getMoodStatement = databaseHandler.getConnection().prepareStatement(getMoodQuery)) {

            getMoodStatement.setString(1, mood.toString());

            ResultSet resultSet = getMoodStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }


    public int getWeaponTypeId(WeaponType weaponType) throws SQLException {

        String getWeaponTypeQuery = "select id from \"Weapon_type\" where name = ?";

        try (PreparedStatement getWeaponTypeStatement = databaseHandler.getConnection().prepareStatement(getWeaponTypeQuery)) {

            getWeaponTypeStatement.setString(1, weaponType.toString());

            ResultSet resultSet = getWeaponTypeStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }


    public int getCarId(Car car) throws SQLException {

        String getCarIdQuery = "select id from \"Cars\" where name = ? and cool = ?";

        try (PreparedStatement getCarIdStatement = databaseHandler.getConnection().prepareStatement(getCarIdQuery)) {

            getCarIdStatement.setString(1, car.getName());
            getCarIdStatement.setBoolean(2, car.getCool());

            ResultSet resultSet = getCarIdStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }


    public int getUserId(String login) throws SQLException {

        String getUserIdQuery = "select id from \"Users\" where login = ?";

        try (PreparedStatement getUserIdStatement = databaseHandler.getConnection().prepareStatement(getUserIdQuery)) {

            getUserIdStatement.setString(1, login);

            ResultSet resultSet = getUserIdStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }


    public int generateHumanBeingPK() throws SQLException {

        String generatePKQuery = "select nextval('human_being_id_seq'::regclass)";

        try (PreparedStatement generatePKStatement = databaseHandler.getConnection().prepareStatement(generatePKQuery)) {
            ResultSet resultSet = generatePKStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }


    public boolean insertHumanBeing(HumanBeing humanBeing, String owner, Long key) throws SQLException {

        if (humanBeing.getCar() != null) {
            insertCar(humanBeing.getCar());
            int carId = getCarId(humanBeing.getCar());
        }

        int humanBeingPK = generateHumanBeingPK();
        humanBeing.setId((long) humanBeingPK);

        int moodId = getMoodId(humanBeing.getMood());
        int weaponTypeId = getWeaponTypeId(humanBeing.getWeaponType());
        int ownerId = getUserId(owner);

        String insertHumanBeingQuery = "insert into \"HumanBeing\" (name, x_coordinate, " +
                "y_coordinate, real_hero, has_toothpick, impact_speed, mood_id, weapon_type_id, " +
                "car_id, creation_date, owner_id, collection_key, \"humanBeing_pk\") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement insertHumanBeingStatement = databaseHandler.getConnection().prepareStatement(insertHumanBeingQuery)) {

            insertHumanBeingStatement.setString(1, humanBeing.getName());
            insertHumanBeingStatement.setDouble(2, humanBeing.getCoordinates().getX());
            insertHumanBeingStatement.setDouble(3, humanBeing.getCoordinates().getY());
            insertHumanBeingStatement.setBoolean(4, humanBeing.isRealHero());
            insertHumanBeingStatement.setBoolean(5, humanBeing.isHasToothpick());
            insertHumanBeingStatement.setDouble(6, humanBeing.getImpactSpeed());
            insertHumanBeingStatement.setInt(7, moodId);
            insertHumanBeingStatement.setInt(8, weaponTypeId);

            if (humanBeing.getCar() != null) {
                insertCar(humanBeing.getCar());
                int carId = getCarId(humanBeing.getCar());
                insertHumanBeingStatement.setInt(9, carId);
            } else {
                insertHumanBeingStatement.setNull(9, Types.NULL);
            }

            insertHumanBeingStatement.setDate(10, Date.valueOf(humanBeing.getCreationDate()));
            insertHumanBeingStatement.setInt(11, ownerId);
            insertHumanBeingStatement.setObject(12, key);
            insertHumanBeingStatement.setInt(13, humanBeingPK);

            insertHumanBeingStatement.executeUpdate();

            return true;

        }
    }


    public boolean removeHumanBeing(Long id) throws SQLException {

        String removeQuery = "delete from \"HumanBeing\" where \"humanBeing_pk\" = ?";

        try (PreparedStatement removeStatement = databaseHandler.getConnection().prepareStatement(removeQuery)) {

            removeStatement.setObject(1, id, Types.BIGINT);

            removeStatement.executeUpdate();

            return true;
        }
    }

    public boolean removeAll(String login) throws SQLException {

        int ownerId = getUserId(login);
        String removeQuery = "delete from \"HumanBeing\" where owner_id = ?";

        try (PreparedStatement removeStatement = databaseHandler.getConnection().prepareStatement(removeQuery)) {

            removeStatement.setInt(1, ownerId);
            removeStatement.executeUpdate();
            return true;
        }
    }
}
