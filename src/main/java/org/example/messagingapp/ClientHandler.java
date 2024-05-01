package org.example.messagingapp;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private String clientUsername;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = reader.readLine();
            clientHandlers.add(this);
        }
        catch (IOException e) {
            closeServer(socket, reader, writer);
            e.printStackTrace();
        }
    }

    //ayrı threadde çalışır
    @Override
    public void run() {
        String messageFromClient;

        while(socket.isConnected()) {
            try
            {
                messageFromClient = reader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeServer(socket, reader, writer);
                break;
            }
        }
    }

    public void broadcastMessage(String message) {
        for (ClientHandler clientHandler: clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(this.clientUsername)) {
                    clientHandler.writer.write(message);
                    clientHandler.writer.newLine();
                    //zorla gönder
                    clientHandler.writer.flush();
                }
            }
            catch(IOException e) {
                closeServer(socket, reader, writer);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
    }

    public void closeServer(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
     }
}
