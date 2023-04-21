package commonModule.commands.commandObjects;

import commonModule.collectionClasses.HumanBeing;
import commonModule.commands.CommandTemplate;
import commonModule.commands.CommandWithResponse;
import commonModule.dataStructures.network.CommandResponse;
import commonModule.dataStructures.network.Response;
import commonModule.dataStructures.network.SizeResponse;
import server.collectionManagement.CollectionManager;

import java.util.Map;

public class GetCollectionSizeCommand extends CommandTemplate implements CommandWithResponse {

    private StringBuilder output;

    public GetCollectionSizeCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }


    @Override
    public void execute() throws Exception {

        output = new StringBuilder();

        Map<Long, HumanBeing> data = getCollectionManager().getCollection();
        if (data.size() % 10 == 0) {
            output.append(data.size() / 10);
        } else {
            output.append(data.size() / 10 + 1);
        }
        // output.append(data.size());
    }

    @Override
    public Response getCommandResponse() {
        return new SizeResponse(output.toString());
    }
}
