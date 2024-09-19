package com.example.inventoryapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin() {
        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();

        String correctPassword = "password";
        String correctUsername = "admin";
        if (enteredUsername.equals(correctUsername) && enteredPassword.equals(correctPassword)) {
            showInventoryScreen();
        } else {
            errorLabel.setText("Invalid username or password");
        }
    }

    private void showInventoryScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("inventory-view.fxml"));
            Scene inventoryScene = new Scene(fxmlLoader.load(), 700, 700);
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.setScene(inventoryScene);
        } catch (IOException e) {
            errorLabel.setText("Failed to load inventory screen.");
        }
    }
}
