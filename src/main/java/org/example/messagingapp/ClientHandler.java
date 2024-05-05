package org.example.messagingapp;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientHandler extends Thread {

    private DatagramSocket socket;
    private byte[] received = new byte[256];

    private VBox vBox;
    private VBox vBoxChats;

    public ClientHandler(DatagramSocket socket, VBox vBox, VBox vBoxChats) {
        this.socket = socket;
        this.vBox = vBox;
        this.vBoxChats = vBoxChats;
    }

    @Override
    public void run() {
        while (true) {
            DatagramPacket packet = new DatagramPacket(received, received.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String message = new String(packet.getData(), 0, packet.getLength()) + "\n";
            Controller.showReceivedMessage(message, vBox, vBoxChats);
        }
    }
}
