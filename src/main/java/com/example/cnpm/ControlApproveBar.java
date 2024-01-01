package com.example.cnpm;

import com.example.cnpm.DatabaseClass.RequestChangeSchedule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ControlApproveBar {
    private RequestChangeSchedule request;
    private VBox findVBoxParent(Node node) {
        Parent parent = node.getParent();
        while (parent != null) {
            if (parent instanceof VBox) {
                return (VBox) parent;
            }
            parent = parent.getParent();
        }
        return null;
    }
    @FXML
    public void approve(MouseEvent mouseEvent) {
        DataBaseConnector db = new DataBaseConnector();
        db.connect();
        db.changeStatusRequests(request.getRequestID(),"Accepted");
        db.disconnect();
        Node sourceNode = (Node) mouseEvent.getSource();
        VBox vbox = findVBoxParent(sourceNode);
        System.out.println(vbox);
        if (vbox != null) {
            vbox.getChildren().remove(sourceNode);
        }
    }
    @FXML
    public void decline(MouseEvent mouseEvent) {
        DataBaseConnector db = new DataBaseConnector();
        db.connect();
        db.changeStatusRequests(request.getRequestID(),"Declined");
        db.disconnect();
        Node sourceNode = (Node) mouseEvent.getSource();
        VBox vbox = findVBoxParent(sourceNode);

        if (vbox != null) {
            vbox.getChildren().remove(sourceNode);
        }
    }
    public void setRequestsData(RequestChangeSchedule request) {
        this.request = request;
    }

}
