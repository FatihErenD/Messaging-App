package org.example.messagingapp;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.*;

public class Client extends Thread {
    private static DatagramSocket socket;
    private static InetAddress address;

    private static final int SERVER_PORT = 1234;

    private String username;
    private VBox vBox;
    private VBox vBoxChats;


    public Client(String username, VBox vBox, VBox vBoxChats) {
        this.username = username;
        this.vBox = vBox;
        this.vBoxChats = vBoxChats;
    }

    @Override
    public void run() {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        }

        ClientHandler clientThread = new ClientHandler(socket, vBox, vBoxChats);
        clientThread.start();

        byte[] saveToServer = ("init;+0000:" + username).getBytes();
        DatagramPacket initialize = new DatagramPacket(saveToServer, saveToServer.length, address, SERVER_PORT);
        try {
            socket.send(initialize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) {
        byte[] msg;
        msg = message.getBytes();
        DatagramPacket send = new DatagramPacket(msg, msg.length, address, SERVER_PORT);
        try {
            socket.send(send);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
