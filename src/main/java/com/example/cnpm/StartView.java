package com.example.cnpm;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class StartView {
    @FXML
    private Pane checkin, login;

    @FXML
    public void onCheckinClick(MouseEvent event) throws IOException {
        Runtime.getRuntime()
                .exec(String.format("cmd.exe /c python %s", "D:\\Work\\Project\\CNPM_ChamCong\\src\\main\\java\\com\\example\\cnpm\\main_video.py"));
    }

    public void onLoginClick(MouseEvent event) throws IOException {
        com.example.cnpm.HelloApplication tmp = new com.example.cnpm.HelloApplication();
        tmp.changeScene("login.fxml");
    }
}
