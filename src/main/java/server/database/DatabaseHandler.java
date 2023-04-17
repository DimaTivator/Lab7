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
            System.out.println("Success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public boolean userExists(String login) throws SQLException {
        String checkUsersExists = "select " +
                "exists (" +
                " select login" +
                " from \"Users\"" +
                " where login = ?);";

        PreparedStatement userExistsStatement = connection.prepareStatement(checkUsersExists);
        userExistsStatement.setString(1, login);

        ResultSet resultSet = userExistsStatement.executeQuery();
        userExistsStatement.close();

        boolean result = resultSet.next() && resultSet.getBoolean(1);
        resultSet.close();

        return result;
    }

    /**
     * Method that adds a new user to database
     */
    public boolean registerUser(String login, String password) throws SQLException {

        if (userExists(login)) {
            return false;
        }

        String addUserQuery = "insert into \"Users\" (login, password) values (?, ?)";

        PreparedStatement registerUserStatement = connection.prepareStatement(addUserQuery);

        registerUserStatement.setString(1, login);
        registerUserStatement.setString(2, password);

        registerUserStatement.execute();
        registerUserStatement.close();

        return true;
    }
}
