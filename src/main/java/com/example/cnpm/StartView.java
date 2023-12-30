package com.example.cnpm;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class StartView {

    @FXML
    public void onCheckinClick(MouseEvent event) throws IOException {
        String pythonScript = "/Users/admin/IdeaProjects/CNPM_ChamCong/src/main/java/com/example/cnpm/main_video.py";

        ProcessBuilder processBuilder = new ProcessBuilder("python3", pythonScript)
                .inheritIO(); // Để chạy Python script với các luồng I/O được kế thừa

        try {
            Process process = processBuilder.start();
            // Đợi quá trình kết thúc
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void onLoginClick(MouseEvent event) throws IOException {
        com.example.cnpm.HelloApplication tmp = new com.example.cnpm.HelloApplication();
        tmp.changeScene("login.fxml");
    }
}
