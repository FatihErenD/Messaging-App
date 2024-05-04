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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
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

    private ArrayList<String> clientToSend = new ArrayList<>();

    @FXML
    private Pane login_screen;

    @FXML
    private Pane create_account;

    @FXML
    private BorderPane main_screen;

    @FXML
    private Label hesap;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label yanlisHesap;

    @FXML
    private Button Create;

    @FXML
    private Label login;

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
    private VBox vBox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox vBox_Messages;

    @FXML
    Pane pane_add;

    @FXML
    private TextField messageField;

    @FXML
    private TextField newUsername;

    private Client client; //= new Client("", vBox_Messages);

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
                            this.client = new Client(clientName, vBox_Messages);
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

            vBox_Messages.getChildren().add(hbox);

            client.sendMessage(codedMessage);
        }
    }

    @FXML
    void onExitClicked(MouseEvent event) {
        vBox.getChildren().clear();
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
            vBox.getChildren().add(hbox);

            String clientSentName;

            clientToSend.add(clientSent);
            focusedClientName = clientSent;
            clientSentName = clientSent;

            hbox.setOnMouseClicked(e -> {
                vBox_Messages.getChildren().clear();
                focusedClientName = clientSentName;
                System.out.println(focusedClientName);
            });

            scrollPane.toFront();
        }
    }

    public static void showReceivedMessage(String receivedMessage, VBox vBox) {
        int indexOfReceiver = receivedMessage.indexOf(":");
        int indexOfCode = receivedMessage.lastIndexOf(":");
        String receiver = receivedMessage.substring(0, indexOfReceiver);
        String sender = receivedMessage.substring(indexOfCode + 1, receivedMessage.length() - 1);
        System.out.println("girdi");
        String encodedMessage = receivedMessage.substring(indexOfReceiver + 1, indexOfCode);
        System.out.println(sender);
        System.out.println(focusedClientName);
        if (sender.equals(focusedClientName)) {
            HBox hbox = new HBox(100);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setPadding(new Insets(5, 5, 5, 10));
            hbox.setSpacing(3);

            Label label = new Label(encodedMessage);
            label.setStyle("""
                    -fx-background-color:  #5f5fd3;
                    -fx-background-radius:  15;
                    -fx-font-family: Consolas;
                    -fx-font-size: 18px;
                    -fx-text-fill: #e1d9ff;""");
            label.setPadding(new Insets(5, 5, 5, 10));
            hbox.getChildren().add(label);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vBox.getChildren().add(hbox);
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        vBox_Messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scrollPane.setVvalue((Double) t1);
            }
        });
    }
}