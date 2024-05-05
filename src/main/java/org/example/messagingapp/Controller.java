package org.example.messagingapp;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private String clientName;
    private String clientSent;
    private static String focusedClientName;

    private static ArrayList<String> clientToSend = new ArrayList<>();
    private static ArrayList<LinkedListForMessages> chats = new ArrayList<>();

    private Client client;
    private static LinkedListForMessages focusedMessages;

    @FXML
    private Pane login_screen;

    @FXML
    private Pane create_account;

    @FXML
    private BorderPane main_screen;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label yanlisHesap;

    @FXML
    private PasswordField password_create1;

    @FXML
    private PasswordField password_create2;

    @FXML
    private TextField username_create;

    @FXML
    private Label hesapYanlis;

    @FXML
    private Label user_name;

    @FXML
    private ScrollPane scrollPaneChats;

    @FXML
    private VBox vBoxChats;

    @FXML
    private ScrollPane scrollPaneMessages;

    @FXML
    private VBox vBox_Messages;

    @FXML
    Pane pane_add;

    @FXML
    private TextField messageField;

    @FXML
    private TextField newUsername;


    @FXML
    void onCreateAccount(MouseEvent event) {
        username.clear();
        password.clear();
        yanlisHesap.setText("");
        create_account.toFront();
    }

    @FXML
    void onAlreadyHave(MouseEvent event) {
        username_create.clear();
        password_create1.clear();
        password_create2.clear();
        hesapYanlis.setText("");
        login_screen.toFront();
    }

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        if (Objects.equals(username.getText(), "") || Objects.equals(password.getText(), "")) {
            yanlisHesap.setText("Kullanıcı adı veya şifrenizi girmediniz.");
        }
        else {
            try (BufferedReader reader = new BufferedReader(new FileReader("hesaplar.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals(username.getText())) {
                        clientName = line;
                        line = reader.readLine();
                        if (Objects.equals(line, password.getText())) {
                            main_screen.toFront();
                            user_name.setText(clientName);
                            username.clear();
                            password.clear();
                            yanlisHesap.setText("");
                            this.client = new Client(clientName, vBox_Messages, vBoxChats);
                            client.start();
                            break;
                        }
                        yanlisHesap.setText("Şifre Yanlış");
                    }
                    line = reader.readLine();
                }
                yanlisHesap.setText("Kullanıcı Bulunamadı");
            } catch (IOException e) {
                System.out.println("Giriş yapılamıyor.");
            }
        }
    }

    @FXML
    void OnCreate(ActionEvent event) {
        try {
            FileWriter fw = new FileWriter("hesaplar.txt", true);
            BufferedWriter writer = new BufferedWriter(fw);

            if (!password_create1.getText().equals(password_create2.getText())) {
                hesapYanlis.setText("Şifreni doğru gir hocam");
                return;
            }

            writer.append(username_create.getText()).append("\n");
            writer.append(password_create1.getText()).append("\n");

            writer.close();
            username_create.clear();
            password_create1.clear();
            password_create2.clear();
            hesapYanlis.setText("");
            login_screen.toFront();

        } catch (IOException e) {
            System.out.println("Kullanıcı Oluşturulamadı");
        }
    }

    @FXML
    void onSentClicked(MouseEvent event) {
        if (!messageField.getText().isEmpty()) {
            String codedMessage;
            String message = messageField.getText();
            messageField.clear();
            codedMessage = (clientSent + ": " +message + " :" + clientName);

            HBox hbox = sentMessageHBox(message);

            vBox_Messages.getChildren().add(hbox);

            focusedMessages.append(message, true);

            client.sendMessage(codedMessage);
        }
    }

    @FXML
    void onExitClicked(MouseEvent event) {
        vBoxChats.getChildren().clear();
        vBox_Messages.getChildren().clear();
        login_screen.toFront();
    }

    @FXML
    void addNew(MouseEvent event) {
        pane_add.toFront();
    }

    @FXML
    void onAddNew(MouseEvent event) {
        if (!newUsername.getText().isEmpty() && !clientToSend.contains(newUsername.getText())) {
            HBox hbox = new HBox();
            hbox.styleProperty().bind(Bindings.when(hbox.hoverProperty())
                    .then("-fx-background-color: #404040;")
                    .otherwise("-fx-background-color: #252525;"));
            hbox.setPadding(new Insets(5, 5, 5, 10));

            clientSent = newUsername.getText();
            newUsername.clear();
            Label label = new Label(clientSent);
            label.setStyle("""
                        -fx-font-family: Consolas;
                        -fx-font-size: 24px;
                        -fx-text-fill: #908ff8;
                        """);
            label.setPadding(new Insets(5, 5, 5, 10));


            hbox.getChildren().add(label);
            vBoxChats.getChildren().add(hbox);

            String clientSentName;

            clientToSend.add(clientSent);
            focusedClientName = clientSent;
            clientSentName = clientSent;
            vBox_Messages.getChildren().clear();

            LinkedListForMessages newMessages = new LinkedListForMessages(clientSent);
            chats.add(newMessages);
            focusedMessages = newMessages;

            hbox.setOnMouseClicked(e -> {
                focusedClientName = clientSentName;
                System.out.println(focusedClientName);
                vBox_Messages.getChildren().clear();
                focusedMessages = newMessages;
                scrollPaneMessages.toFront();

                int size = focusedMessages.getSize();
                LinkedListForMessages.Node temp = focusedMessages.first;

                for (int i = 0; i < size; i++) {
                    HBox messageHBox;
                    if (temp.isSenderMessage())
                        messageHBox = sentMessageHBox(temp.getMessageInfo());
                    else
                        messageHBox = receivedMessageHBox(temp.getMessageInfo());
                    temp = temp.next;
                    vBox_Messages.getChildren().add(messageHBox);
                }
            });

            scrollPaneMessages.toFront();
            vBox_Messages.getChildren().clear();
        }
    }

    private static HBox sentMessageHBox(String message) {
        HBox hbox = new HBox(100);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setPadding(new Insets(5, 5, 5, 10));
        hbox.setSpacing(3);

        Label label = new Label(message);
        label.setStyle("""
                        -fx-background-color:  #252525;
                        -fx-background-radius:  15;
                        -fx-font-family: Consolas;
                        -fx-font-size: 18px;
                        -fx-text-fill: #e1d9ff;""");
        label.setPadding(new Insets(5, 5, 5, 10));
        hbox.getChildren().add(label);

        return hbox;
    }

    private static HBox receivedMessageHBox(String message) {
        HBox hbox = new HBox(100);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(5, 5, 5, 10));
        hbox.setSpacing(3);

        Label label = new Label(message);
        label.setStyle("""
                    -fx-background-color:  #5f5fd3;
                    -fx-background-radius:  15;
                    -fx-font-family: Consolas;
                    -fx-font-size: 18px;
                    -fx-text-fill: #e1d9ff;""");
        label.setPadding(new Insets(5, 5, 5, 10));
        hbox.getChildren().add(label);

        return hbox;
    }

    public static void showReceivedMessage(String receivedMessage, VBox vBox, VBox vBoxChats) {
        int indexOfReceiver = receivedMessage.indexOf(":");
        int indexOfCode = receivedMessage.lastIndexOf(":");

        String sender = receivedMessage.substring(indexOfCode + 1, receivedMessage.length() - 1);
        String encodedMessage = receivedMessage.substring(indexOfReceiver + 1, indexOfCode);

        if (sender.equals(focusedClientName)) {
            HBox hbox = receivedMessageHBox(encodedMessage);

            focusedMessages.append(encodedMessage, false);

            if (!clientToSend.contains(sender))
                clientToSend.add(sender);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vBox.getChildren().add(hbox);
                }
            });
        } else {
            if (!clientToSend.contains(sender))
                clientToSend.add(sender);

            int chatIndex = -1;
            for (int i = 0; i < chats.size(); i++) {
                System.out.println(chats.get(i).getChatName());
                if (Objects.equals(chats.get(i).getChatName(), sender)) {
                    chatIndex = i;
                    System.out.println("girdi: " + chatIndex);
                    break;
                }
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Yeni Mesajınız Var");
                    alert.setHeaderText(null);
                    alert.setContentText(sender + " size mesaj gönderdi.");
                    alert.showAndWait();
                }
            });
            if (chatIndex == -1) {
                HBox hbox = new HBox();
                hbox.styleProperty().bind(Bindings.when(hbox.hoverProperty())
                        .then("-fx-background-color: #404040;")
                        .otherwise("-fx-background-color: #252525;"));
                hbox.setPadding(new Insets(5, 5, 5, 10));

                Label label = new Label(sender);
                label.setStyle("""
                        -fx-font-family: Consolas;
                        -fx-font-size: 24px;
                        -fx-text-fill: #908ff8;
                        """);
                label.setPadding(new Insets(5, 5, 5, 10));

                hbox.getChildren().add(label);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        vBoxChats.getChildren().add(hbox);
                    }
                });

                LinkedListForMessages newMessages = new LinkedListForMessages(sender);
                chats.add(newMessages);

                hbox.setOnMouseClicked(e -> {
                    focusedClientName = newMessages.getChatName();
                    vBox.getChildren().clear();
                    focusedMessages = newMessages;
                    vBox.toFront();

                    int size = focusedMessages.getSize();
                    LinkedListForMessages.Node temp = focusedMessages.first;

                    for (int i = 0; i < size; i++) {
                        HBox messageHBox;
                        if (temp.isSenderMessage())
                            messageHBox = sentMessageHBox(temp.getMessageInfo());
                        else
                            messageHBox = receivedMessageHBox(temp.getMessageInfo());
                        temp = temp.next;
                        vBox.getChildren().add(messageHBox);
                    }
                });
            }
            chatIndex = chats.size() - 1;

            chats.get(chatIndex).append(encodedMessage, false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPaneMessages.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneMessages.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollPaneChats.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneChats.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        vBoxChats.toFront();


        vBox_Messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scrollPaneMessages.setVvalue((Double) t1);
            }
        });
    }
}