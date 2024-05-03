package org.example.messagingapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Scene scene;
    private Stage stage;
    private Parent root;
    private Client client;

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
    private VBox vBox_Messages;

    @FXML
    private TextField messageField;

    @FXML
    void onCreateAccount(MouseEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "create");
    }

    @FXML
    void onAlreadyHave(MouseEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "login");
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
                        line = reader.readLine();
                        if (Objects.equals(line, password.getText())) {
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            changeScene(stage, "main");
                            break;
                        }
                        yanlisHesap.setText("Şifre Yanlış");
                    }
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

            writer.append(username_create.getText()+"\n");
            writer.append(password_create1.getText()+"\n");

            writer.close();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            changeScene(stage, "login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSentClicked(MouseEvent event) {
        if (messageField.getText() != "") {
            String message = messageField.getText();
            messageField.clear();
            client.sendMessage(message);

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
            hbox.getChildren().add(label);

            vBox_Messages.getChildren().add(hbox);
        }
    }

    @FXML
    void onExitClicked(MouseEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        changeScene(stage, "login");
    }

    private void changeScene(Stage stage, String fxmlName) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlName + ".fxml")));
            scene = new Scene(root);
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Yüklenemedi...");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            client = new Client(new Socket("localhost", 1234));
        } catch (IOException e) {
            System.out.println("Kullanıcı oluşturulamadı.");
        }
        client.receiveMessage();
    }
}