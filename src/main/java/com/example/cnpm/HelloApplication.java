package com.example.cnpm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage currentStage;
    @Override
    public void start(Stage stage) throws IOException {
        currentStage = stage;
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("start-view.fxml"));
        stage.setTitle("Hệ thống quản lý chấm công");
        stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
        stage.show();
    }
    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        currentStage.getScene().setRoot(pane);
    }
    public static void main(String[] args) {
        launch();
    }
}