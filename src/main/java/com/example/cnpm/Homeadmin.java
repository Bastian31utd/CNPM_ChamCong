package com.example.cnpm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class Homeadmin {

    @FXML
    private Button currentlist;

    @FXML
    private Button rankingall;

    @FXML
    private Button rankingalldepartment;

    @FXML
    private Button requestsall;

    @FXML
    private Label setname;

    @FXML
    private Button scheduleall;

    @FXML
    private Label setemail;

    @FXML
    private Label setphone;
    private String userID; // Thêm trường dữ liệu để lưu UserID

    // Hiểm thị thông tin admin
    public void setUserID(String userID) {
        this.userID = userID;
        // Thực hiện truy vấn để lấy Name từ database dựa trên userID
        DataBaseConnector connector = new DataBaseConnector();
        connector.connect();

        // Gọi phương thức để lấy Name từ UserID
        String userName = connector.getUserInfoByUserID(userID,1);
        // Hiển thị Name lên Label setname
        setname.setText(userName+" ( Admin )");

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
    void currentlist(ActionEvent event) throws IOException {
        HelloApplication change = new HelloApplication();
        change.changeSceneToWorkSchedule2("WorkSchedule2.fxml", userID);
    }

    @FXML
    void rankingall(ActionEvent event) throws IOException {
        HelloApplication change = new HelloApplication();
        change.changeSceneToPersonalRanking("PersonalRanking.fxml", userID);

    }
    @FXML
    void rankingalldepartment(ActionEvent event) throws IOException {
        HelloApplication change = new HelloApplication();
        change.changeSceneToPersonalRanking2("PersonalRanking2.fxml", userID);

    }

    @FXML
    void requestsall(ActionEvent event) {

    }

    @FXML
    void scheduleall(ActionEvent event) {

    }
    @FXML
    void logout(ActionEvent event) throws IOException {
        HelloApplication change = new HelloApplication();
        change.changeScene("login.fxml");
    }

}
