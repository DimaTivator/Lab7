package client;

import commonModule.dataStructures.network.Request;
import commonModule.dataStructures.network.Response;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class NetworkProvider {

    private final int BUFFER_SIZE = 8192 * 8192;

    private final DatagramSocket datagramSocket;

    private final int port;


    public NetworkProvider(String address, int port) throws IOException {

        this.port = port;

        datagramSocket = new DatagramSocket();
        datagramSocket.setSoTimeout(80000);

        System.out.println("===== Client started! Server on " + address + ":" + port + " =====");
    }


    public void send(Request request) {

        ObjectOutputStream objectOutputStream = null;

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(request);

            InetAddress host = InetAddress.getLocalHost();
            DatagramPacket datagramPacket = new DatagramPacket(out.toByteArray(), out.size(), host, port);

            datagramSocket.send(datagramPacket);

        } catch (IOException e) {

            System.out.println(e.getMessage());

        } finally {
            try {
                assert objectOutputStream != null;
                objectOutputStream.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public Response receive() {

        Response response;

        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        DatagramPacket datagramPacket = new DatagramPacket(buffer.array(), buffer.array().length);

        try {
            datagramSocket.receive(datagramPacket);

            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            response = (Response) objectInputStream.readObject();

            return response;
        }

        catch (IOException ignored) {}

        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
