package server.Threads;

import commonModule.dataStructures.network.AuthenticationRequest;
import commonModule.dataStructures.network.AuthenticationResponse;
import commonModule.dataStructures.network.Request;
import commonModule.exceptions.InvalidInputException;
import commonModule.exceptions.commandExceptions.InvalidArgumentsException;
import org.slf4j.Logger;
import server.NetworkProvider;
import server.database.DatabaseHandler;

import java.sql.SQLException;

public class AuthenticationThread extends Thread {

    public AuthenticationThread(DatabaseHandler databaseHandler, NetworkProvider networkProvider,
                                Logger logger, AuthenticationRequest request) {
        super(() -> {

            AuthenticationResponse response = new AuthenticationResponse();
            String login = request.getLogin();
            String password = request.getPassword();

            try {

                if (request.isNewUser()) {
                    boolean registrationResult = databaseHandler.registerUser(login, password);
                    response.setAuthenticated(registrationResult);

                    if (!registrationResult) {
                        response.setException(new InvalidInputException("User with the entered login already exists"));
                    }

                } else {
                    response.setAuthenticated(true);

                    if (!databaseHandler.userExists(login)) {
                        response.setAuthenticated(false);
                        response.setException(new InvalidInputException("Unknown login"));
                    }

                    if (databaseHandler.userExists(login) && !databaseHandler.getUsersPassword(login).equals(password)) {
                        response.setAuthenticated(false);
                        response.setException(new InvalidInputException("Invalid password"));
                    }
                }

            } catch (SQLException e) {
                response.setAuthenticated(false);
                response.setException(e);
            }

            Thread responseSenderThread = new ResponseSenderThread(response, networkProvider, request.getHost(), 1);
            responseSenderThread.start();
        });
    }
}
