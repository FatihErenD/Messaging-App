package org.example.messagingapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String isim;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            closeServer(socket, reader, writer);
        }
    }

    public void sendMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            closeServer(socket, reader, writer);
        }
    }

    public void receiveMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String receivedMessage;
                while (socket.isConnected()) {
                    try {
                        receivedMessage = reader.readLine();
                        System.out.println(receivedMessage);
                    } catch (IOException e) {
                        closeServer(socket, reader, writer);
                    }
                }
            }
        }).start();
    }

    public void closeServer(Socket socket, BufferedReader reader, BufferedWriter writer) {
        try {
            if (reader != null)
                reader.close();
            if (writer != null)
                socket.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            System.out.println("Hata...");
        }
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket);
        client.receiveMessage();
    }
}
