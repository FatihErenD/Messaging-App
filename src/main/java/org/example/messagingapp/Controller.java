package org.example.messagingapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import org.w3c.dom.Text;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
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
    private TextField name_create;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            server = new Server(new ServerSocket(1234));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server olusturulamadi");
        }

        server.receiveMessageFromClient(vBox_Messages);

        vBox_Messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

            }
        });

    }



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
        String message = messageField.getText();
        if (!message.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER_RIGHT);
            hbox.setPadding(new Insets(5, 5, 5, 10));

        }
    }

    @FXML
    void onExitClicked(MouseEvent event) {

    }


}