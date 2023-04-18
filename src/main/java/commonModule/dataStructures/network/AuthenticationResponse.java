package commonModule.dataStructures.network;

public class AuthenticationResponse implements Response {

    private boolean authenticated;
    private Exception exception;

    public AuthenticationResponse() {}

    public AuthenticationResponse(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
