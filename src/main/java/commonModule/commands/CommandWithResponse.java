package commonModule.commands;

import commonModule.dataStructures.Response;

public interface CommandWithResponse extends Command {

    Response getCommandResponse();
}
