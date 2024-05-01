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

    }

    @FXML
    void OnCreate(ActionEvent event) {

    }

    @FXML
    void onSentClicked(MouseEvent event) {

    }

    @FXML
    void onExitClicked(MouseEvent event) {

    }


}