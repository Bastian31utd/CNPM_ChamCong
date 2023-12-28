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
    void forgotpass(ActionEvent event) throws IOException {
        HelloApplication change= new HelloApplication();
        change.changeScene("forgotpass.fxml");
    }

    @FXML
    public void loginbutton(ActionEvent event) throws IOException {
        checklogin();
    }
    private void checklogin() throws IOException {
        HelloApplication m = new HelloApplication();

        if (username.getText().isEmpty() || pass.getText().isEmpty()) {
            wronglogin.setText("Please enter your data.");
        } else {
            // Sử dụng thử phương thức kiemTraDangNhap từ DataBaseConnector
            DataBaseConnector connector = new DataBaseConnector();
            connector.connect();

            // Gọi phương thức kiemTraDangNhap
            String userID = connector.kiemTraDangNhap(username.getText(), pass.getText());

            if (userID != null) {
                // Kiểm tra RoleID
                int roleID = connector.getRoleID(userID);
                // Thực hiện các xử lý tiếp theo dựa trên RoleID
                if (roleID == 1) {
                    m.changeSceneToHomeAdmin("homeadmin.fxml", userID);
                } else {
                    m.changeSceneToHomeuser("homeuser.fxml", userID);
                }
            }
            else {
                wronglogin.setText("Login failed");
            }
        }
    }
}