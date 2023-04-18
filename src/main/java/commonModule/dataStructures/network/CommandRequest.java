package commonModule.dataStructures.network;

import commonModule.commands.Command;

import java.io.Serializable;

public class CommandRequest extends Request {

    private Serializable command;

    public CommandRequest(Command commandObject) {
        this.command = (Serializable) commandObject;
    }

    public Serializable getCommand() {
        return command;
    }

    public void setCommand(Serializable command) {
        this.command = command;
    }
}
