package org.example.messagingapp;


import java.io.IOException;
import java.net.*;
import java.util.ArrayList;


public class Server {
    private static byte[] received = new byte[256];
    private static final int PORT = 1234;

    private static DatagramSocket socket;

    private static ArrayList<Integer> clientPorts = new ArrayList<>();
    private static ArrayList<String> clientNames = new ArrayList<>();
    private static InetAddress address;


    public static void main(String[] args) {
        try {
            address = InetAddress.getByName("localhost");
            socket = new DatagramSocket(PORT);
        } catch (UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server started on port " + PORT);

        while (true) {
            DatagramPacket packet = new DatagramPacket(received, received.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Server received: " + message);


            if (message.contains("init;+0000:")) {
                clientPorts.add(packet.getPort());
                int indexOfClientName = message.indexOf(":");
                String clientName = message.substring(indexOfClientName + 1);
                clientNames.add(clientName);
            }
            else {
                int indexOfReceiverName = message.indexOf(":");
                String receiver = message.substring(0, indexOfReceiverName);

                byte[] byteMessage = message.getBytes();


                int receiverIndex = clientNames.indexOf(receiver);
                if (receiverIndex != -1) {
                    int receiverPort = clientPorts.get(receiverIndex);

                    DatagramPacket packetToSent = new DatagramPacket(byteMessage, byteMessage.length, address, receiverPort);
                    try {
                        socket.send(packetToSent);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
