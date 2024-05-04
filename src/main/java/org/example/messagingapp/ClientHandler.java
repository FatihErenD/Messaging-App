package org.example.messagingapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientHandler extends Thread {

    private DatagramSocket socket;
    private byte[] incoming = new byte[256];

    private TextField textField;
    private VBox vBox;

    public ClientHandler(DatagramSocket socket, VBox vBox) {
        this.socket = socket;
        this.vBox = vBox;
    }

    @Override
    public void run() {
        System.out.println("starting thread");
        while (true) {
            DatagramPacket packet = new DatagramPacket(incoming, incoming.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String message = new String(packet.getData(), 0, packet.getLength()) + "\n";
            Controller.showReceivedMessage(message, vBox);
        }
    }
}
