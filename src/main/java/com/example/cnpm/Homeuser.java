package com.example.cnpm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Homeuser {

    @FXML
    private Button changeschedule;

    @FXML
    private Button editprofile;

    @FXML
    private Button viewschedule;

    @FXML
    private Label setemail;

    @FXML
    private Label setname;

    @FXML
    private Label setphone;
    private String userID; // Thêm trường dữ liệu để lưu UserID

    // Hiển thị thông tin User
    public void setUserID(String userID) {
        this.userID = userID;
        // Thực hiện truy vấn để lấy Name từ database dựa trên userID
        DataBaseConnector connector = new DataBaseConnector();
        connector.connect();

        // Gọi phương thức để lấy Name từ UserID
        String userName = connector.getUserInfoByUserID(userID,1);
        // Hiển thị Name lên Label setname
        setname.setText(userName+" ( User )");

        // Gọi phương thức để lấy Phone từ UserID
        String Phone = connector.getUserInfoByUserID(userID,2);
        // Hiển thị phone lên Label setphone
        setphone.setText(Phone);

        // Gọi phương thức để lấy email từ UserID
        String Email1 = connector.getUserInfoByUserID(userID,3);
        // Hiển thị email lên Label setemail
        setemail.setText(Email1);
    }

    @FXML
    void changeschedule(ActionEvent event) {

    }

    @FXML
    void editprofile(ActionEvent event) {

    }

    @FXML
    void viewschedule(ActionEvent event) {

    }

}
