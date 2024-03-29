package eu.telecomnancy.labfx;

import java.io.IOException;

import eu.telecomnancy.labfx.model.JSONDatabase;
import eu.telecomnancy.labfx.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Login {

    public Main main;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Button createAccountButton; // Add a button for creating an account

    public Login(Main main) {
        this.main = main;
    }

    private void tryLogin() throws IOException {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        int authenticatedUID = JSONDatabase.getInstance().authenticate(enteredUsername, enteredPassword);

        if (authenticatedUID != -1) {
            User user = JSONDatabase.getInstance().getUser(authenticatedUID);
            main.mainScreen(user);
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid login credentials", "Please check your username and password");
        }
    }

    @FXML
    public void clickButton() throws IOException {
        tryLogin();
    }

    @FXML
    public void createAccount() throws IOException {
        main.createAccountScreen();
    }

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    @FXML
    private void checkEnterPassword(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            tryLogin();
        }
    }

    @FXML
    private void checkEnterUser(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            password.requestFocus();
        }
    }
}
