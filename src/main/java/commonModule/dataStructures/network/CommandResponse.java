package commonModule.dataStructures.network;



import java.io.Serializable;

public class CommandResponse implements Response {

    public String command;
    public String output;
    public String[] args;
    public Serializable object;

    public CommandResponse(String command, String[] args, String output) {
        this.command = command;
        this.output = output;
    }

    public CommandResponse(String command, String[] args, String output, Serializable object) {
        this.command = command;
        this.output = output;
        this.object = object;
    }

    public String getCommand() {
        return command;
    }

    public String getOutput() {
        return output;
    }

    public Serializable getObject() {
        return object;
    }
}
