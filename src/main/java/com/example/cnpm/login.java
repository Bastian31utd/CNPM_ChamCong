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
    public void loginbutton(ActionEvent event) throws IOException {
        checklogin();
    }
    private void checklogin() throws IOException{
        HelloApplication m= new HelloApplication();
        //Check thông tin đăng nhập hợp lệ (đang lấy vd là javacoding với 123)
        if(username.getText().toString().equals("javacoding") && pass.getText().toString().equals("123")) { wronglogin.setText("Success!");
            m.changeScene("home.fxml");
        }
        //kiểm tra nếu 2 trường đều trống
        else if(username.getText().isEmpty() && pass.getText().isEmpty()) { wronglogin.setText("Please enter your data.");
        }
        //nếu thếu 1 trong 2
        else {
            wronglogin.setText("Wrong username or password!");
        }
    }
    }
