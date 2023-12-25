package com.example.cnpm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class forgotpass {

    @FXML
    private Label done;

    @FXML
    private TextField emailinfor;

    @FXML
    private Button sendrequest;

    @FXML
    public void sendrequest(ActionEvent event) throws IOException {
        checkrequest();
    }
    private void checkrequest() throws IOException {
        if(emailinfor.getText().isEmpty() || emailinfor.getText().toString().equals("Your email")) {
            done.setText("Your data is empty");
        }
        else {
            done.setText("Your request has just been sent.");
        }
    }

}
