package org.example.messagingapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.util.Objects;

public class Controller {
    private Scene scene;
    private Stage stage;
    private Parent root;

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

    private Server server;




    @FXML
    void onCreateAccount(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("create.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onAlreadyHave(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
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
                            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setTitle("");
                            stage.setScene(scene);
                            stage.show();
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

            writer.append(username_create.getText()+"\n");
            writer.append(password_create1.getText()+"\n");

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSentClicked(MouseEvent event) {

    }

    @FXML
    void onExitClicked(MouseEvent event) {

    }


}