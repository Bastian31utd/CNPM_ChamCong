package com.example.cnpm;

import com.example.cnpm.DatabaseClass.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AfterLoginUser extends Application {
    private static Stage currentStage;
    @Override
    public void start(Stage stage) throws IOException {
        currentStage = stage;
        stage.setResizable(false);
        DataBaseConnector db = new DataBaseConnector();
        db.connect();
        User user = db.getUserProfileFromId("C31");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminProfile.fxml"));
        Parent root = fxmlLoader.load();
        // Create a new stage
        AdminProfile controller = fxmlLoader.getController();
        db.disconnect();
        System.out.println(user.getName());
        controller.setUser(user);
        stage.setTitle("Hệ thống quản lý chấm công");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root, 600, 400));
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