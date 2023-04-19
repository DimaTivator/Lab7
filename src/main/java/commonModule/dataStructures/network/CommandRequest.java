package commonModule.dataStructures.network;

import commonModule.commands.Command;

import java.io.Serializable;

public class CommandRequest extends Request {

    private Serializable command;
    private String login;
    private String password;

    public CommandRequest(Command commandObject) {
        this.command = (Serializable) commandObject;
    }

    public Serializable getCommand() {
        return command;
    }

    public void setCommand(Serializable command) {
        this.command = command;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
