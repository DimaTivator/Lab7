package server.Threads;

import commonModule.dataStructures.network.Response;
import server.NetworkProvider;

import java.net.SocketAddress;

public class ResponseSenderThread extends Thread {

    public ResponseSenderThread(Response response, NetworkProvider networkProvider, SocketAddress client) {
        super(() -> {
            networkProvider.send(response, client);
        });
    }
}
