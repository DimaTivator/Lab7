package server;

import commonModule.dataStructures.Request;
import commonModule.dataStructures.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class NetworkProvider {

    private static final Logger logger = LoggerFactory.getLogger(NetworkProvider.class);

    private final int BUFFER_SIZE = 1024 * 1024;

    private final DatagramSocket serverSocket;

    public NetworkProvider(int port) throws IOException {

        DatagramChannel datagramChannel = DatagramChannel.open();
        // datagramChannel.configureBlocking(false);

        serverSocket = datagramChannel.socket();
        serverSocket.setSoTimeout(100);
        serverSocket.bind(new InetSocketAddress(port));
    }

    public Request receive() {

        Request request;

        try {

            ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
            DatagramPacket datagramPacket = new DatagramPacket(buf.array(), buf.array().length);

            serverSocket.receive(datagramPacket);
            SocketAddress client = datagramPacket.getSocketAddress();

            logger.info("Packet from client {} successfully received", client);

            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buf.array()));

            request = (Request) objectInputStream.readObject();
            request.setHost(client);

            logger.info("Packet from client {} successfully unpacked", client);

            return request;

        } catch (IOException ignored) {}

        catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    public void send(Response response, SocketAddress client) {

        // ObjectOutputStream objectOutputStream = null;

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(out)) {

            objectOutputStream.writeObject(response);

            DatagramPacket responsePacket = new DatagramPacket(out.toByteArray(), out.toByteArray().length);
            responsePacket.setSocketAddress(client);

            serverSocket.send(responsePacket);

            logger.info("Response successfully sent to {}", client);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
