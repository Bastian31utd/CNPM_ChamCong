package com.example.cnpm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;


import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stg;
    @Override
    public void start(Stage primaryStage) throws Exception{
        stg=primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("start-view.fxml"));
        //primaryStage.setTitle("Log in");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }
    public void changeSceneToHomeAdmin(String fxml, String userID) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent parent = loader.load();

        // Truyền UserID vào Homeadmin controller
        Homeadmin homeadminController = loader.getController();
        homeadminController.setUserID(userID);

        Scene scene = new Scene(parent);
        stg.setScene(scene);
        stg.show();
    }
    public void changeSceneToHomeuser(String fxml, String userID) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent parent = loader.load();

        // Truyền UserID vào Homeadmin controller
        Homeuser homeadminController = loader.getController();
        homeadminController.setUserID(userID);

        Scene scene = new Scene(parent);
        stg.setScene(scene);
        stg.show();
    }
    public void changeSceneToWorkSchedule2(String fxml, String userID) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent parent = loader.load();

        // Truyền UserID vào Homeadmin controller
        WorkSchedule2 homeadminController = loader.getController();
        homeadminController.setUserID(userID);

        Scene scene = new Scene(parent);
        stg.setScene(scene);
        stg.show();
    }
    public void changeSceneToPersonalRanking(String fxml, String userID) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent parent = loader.load();

        // Truyền UserID vào Homeadmin controller
        PersonalRanking homeadminController = loader.getController();
        homeadminController.setUserID(userID);

        Scene scene = new Scene(parent);
        stg.setScene(scene);
        stg.show();
    }
    public void changeSceneToPersonalRanking2(String fxml, String userID) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent parent = loader.load();

        // Truyền UserID vào Homeadmin controller
        PersonalRanking2 homeadminController = loader.getController();
        homeadminController.setUserID(userID);

        Scene scene = new Scene(parent);
        stg.setScene(scene);
        stg.show();
    }

    public static void main(String[] args) { launch(args); }
}
