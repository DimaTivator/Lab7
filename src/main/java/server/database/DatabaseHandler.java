package server.database;

import java.sql.*;

public class DatabaseHandler {

    private final String URL;
    private final String username;
    private final String password;
    private Connection connection;

    public DatabaseHandler(String URL, String username, String password) {
        this.URL = URL;
        this.username = username;
        this.password = password;
    }

    /**
     * Method that provides the connection to database
     */
    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }


    public Connection getConnection() {
        return connection;
    }


    public synchronized boolean userExists(String login) throws SQLException {
        String checkUsersExists = "select " +
                "exists (" +
                " select login" +
                " from \"Users\"" +
                " where login = ?);";

        try (PreparedStatement userExistsStatement = connection.prepareStatement(checkUsersExists)) {
            userExistsStatement.setString(1, login);

            ResultSet resultSet = userExistsStatement.executeQuery();

            return resultSet.next() && resultSet.getBoolean(1);
        }
    }

    /**
     * Method that adds a new user to database
     */
    public synchronized boolean registerUser(String login, String password) throws SQLException {

        if (userExists(login)) {
            return false;
        }

        String addUserQuery = "insert into \"Users\" (login, password) values (?, ?)";

        try (PreparedStatement registerUserStatement = connection.prepareStatement(addUserQuery)) {

            registerUserStatement.setString(1, login);
            registerUserStatement.setString(2, password);

            registerUserStatement.executeUpdate();
        }

        return true;
    }

    public synchronized String getUsersPassword(String login) throws SQLException {

        String getPasswordQuery = "select password from \"Users\" where login = ?";

        try (PreparedStatement getPasswordStatement = connection.prepareStatement(getPasswordQuery)) {

            getPasswordStatement.setString(1, login);

            ResultSet resultSet = getPasswordStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                return null;
            }
        }
    }
}
