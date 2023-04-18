package commonModule.dataStructures.network;

public class AuthenticationRequest extends Request {

    private final boolean newUser;
    private final String login;
    private final String password;

    public AuthenticationRequest(boolean newUser, String login, String password) {
        this.newUser = newUser;
        this.login = login;
        this.password = password;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
