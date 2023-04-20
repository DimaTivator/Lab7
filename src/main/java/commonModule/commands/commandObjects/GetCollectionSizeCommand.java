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
        if (data.size() % 50 == 0) {
            output.append(data.size() / 200);
        } else {
            output.append(data.size() / 200 + 1);
        }
    }

    @Override
    public Response getCommandResponse() {
        return new SizeResponse(output.toString());
    }
}
