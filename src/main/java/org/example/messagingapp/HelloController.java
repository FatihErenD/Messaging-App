package org.example.messagingapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

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
    void onCreateAccount(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onLoginButtonClick(ActionEvent event) {

    }

    @FXML
    void onMouseEntered(MouseEvent event) {

    }

    @FXML
    void OnCreate(ActionEvent event) {

    }

    @FXML
    void onAlreadyHave(MouseEvent event) {

    }

}