package commonModule.dataStructures;

import commonModule.commands.Command;

import java.io.Serializable;
import java.net.SocketAddress;

public class Request implements Serializable {


    private Serializable command;

    private SocketAddress host;

    public Request(Command commandObject) {
        this.command = (Serializable) commandObject;
    }


    public Serializable getCommand() {
        return command;
    }

    public void setCommand(Serializable command) {
        this.command = command;
    }


    public SocketAddress getHost() {
        return host;
    }

    public void setHost(SocketAddress host) {
        this.host = host;
    }
}
