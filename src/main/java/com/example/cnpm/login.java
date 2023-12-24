package com.example.cnpm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class login {

    @FXML
    private PasswordField pass;

    @FXML
    private TextField username;

    @FXML
    private Label wronglogin;

    @FXML
    public void loginButton(ActionEvent event) throws IOException {
        checkLogin();
    }

    public void backToStartView(ActionEvent event) throws IOException {
        HelloApplication tmp = new HelloApplication();
        tmp.changeScene("start-view.fxml");
    }

    private void checkLogin() throws IOException {
        HelloApplication tmp = new HelloApplication();
        // Check thông tin đăng nhập hợp lệ (đang lấy vd là javacoding với 123)
        if (username.getText().toString().equals("javacoding") && pass.getText().toString().equals("123")) {
            wronglogin.setText("Success!");
            tmp.changeScene("home.fxml");
        }
        // Kiểm tra nếu 2 trường đều trống
        else
        if(username.getText().isEmpty() && pass.getText().isEmpty()) {
            wronglogin.setText("Please enter your data.");
        }
        // Nếu thếu 1 trong 2
        else {
            wronglogin.setText("Wrong username or password!");
        }
    }
}
