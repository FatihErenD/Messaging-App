package org.example.messagingapp;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bfReader;
    private BufferedWriter bfWriter;

    private String clientUsername;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bfWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bfReader = new BufferedReader(new InputStreamWriter(socket.getInputStream()));
            this.clientUsername = bfReader.readLine();
            clientHandlers.add(this);
            broadcastMessage(clientUsername + "gruba katıldı!");
        }
        catch (IOException e) {
            closeEverything(socket, bfReader, bfWriter);
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
                messageFromClient = bfReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, bfReader, bfWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String message) {
        for (ClientHandler clientHandler: clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(this.clientUsername)) {
                    clientHandler.bfWriter.write(message);
                    clientHandler.bfWriter.newLine();
                    //zorla gönder
                    clientHandler.bfWriter.flush();
                }
            }
            catch(IOException e) {
                closeEverything(socket, bfReader, bfWriter);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage(clientUsername + "sohbetten ayrıldı.");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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
