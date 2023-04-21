package server.Threads;

import commonModule.dataStructures.network.CommandResponse;
import commonModule.dataStructures.network.Response;
import commonModule.dataStructures.network.SizeResponse;
import server.NetworkProvider;

import java.net.SocketAddress;

public class ResponseSenderThread extends Thread {

    public ResponseSenderThread(Response response, NetworkProvider networkProvider, SocketAddress client, int numberOfResponses) {
        super(() -> {

            if (numberOfResponses == 1) {
                networkProvider.send(response, client);

            } else {

                String output = ((CommandResponse) response).getOutput();

                int length = output.length();
                int substrLength = length / numberOfResponses; // длина подстрок, кроме последней
                int remainingLength = length % numberOfResponses; // остаток от деления длины строки на n
                String[] substrings = new String[numberOfResponses];
                int startIndex = 0; // начальный индекс для извлечения подстроки

                for (int i = 0; i < numberOfResponses - 1; i++) {
                    substrings[i] = output.substring(startIndex, startIndex + substrLength);
                    startIndex += substrLength;
                }

                substrings[numberOfResponses - 1] = output.substring(startIndex, startIndex + substrLength + remainingLength);

                for (int i = 0; i < numberOfResponses; i++) {

                    CommandResponse commandResponse = new CommandResponse(
                            ((CommandResponse) response).getCommand(),
                            null,
                            substrings[i]
                    );

                    networkProvider.send(commandResponse, client);

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ignored) {}
                }
            }
        });
    }
}
