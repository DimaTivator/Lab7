package commonModule.commands;

import commonModule.dataStructures.network.Response;

public interface CommandWithResponse extends Command {

    Response getCommandResponse();
}
