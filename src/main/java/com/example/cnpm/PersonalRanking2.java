package com.example.cnpm;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PersonalRanking2 implements Initializable {

    @FXML
    private TableView<PersonalRankingRow2> departmentRankingTableView;

    @FXML
    private TableColumn<PersonalRankingRow2, String> rankColumn;

    @FXML
    private TableColumn<PersonalRankingRow2, String> departmentIDColumn;

    @FXML
    private TableColumn<PersonalRankingRow2, String> departmentNameColumn;
    private String userID; // Thêm trường dữ liệu để lưu UserID

    public void setUserID(String userID) {
        this.userID = userID;
    }

    DataBaseConnector db = new DataBaseConnector();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rankColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getrank()));
        departmentIDColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartmentID()));
        departmentNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartmentName()));


    }

    private void departmentsRanking() {
        try {
            db.connect();
            String query = "SELECT d.DepartmentID, d.DepartmentName, " +
                    "SUM((SELECT COUNT(*) FROM LeaveRequest WHERE UserID = u.UserID AND RequestType = 'Tăng ca' AND Status = 'Confirm') * 3 " +
                    "- (SELECT COUNT(*) FROM Attendance WHERE UserID = u.UserID AND Late = true) * 2 " +
                    "- (SELECT COUNT(*) FROM LeaveRequest WHERE UserID = u.UserID AND RequestType = 'Nghỉ phép' AND Status = 'Confirm') * 5) AS TotalPoints, " +
                    "COUNT(u.UserID) AS EmployeeCount " +
                    "FROM Users u " +
                    "JOIN Departments d ON u.DepartmentID = d.DepartmentID " +
                    "GROUP BY d.DepartmentID, d.DepartmentName";

            PreparedStatement statement = db.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            ObservableList<PersonalRankingRow2> rankings = FXCollections.observableArrayList();

            int rank = 1;
            while (resultSet.next()) {
                int departmentId = resultSet.getInt("DepartmentID");
                String departmentName = resultSet.getString("DepartmentName");

                // Create PersonalRankingRow2 object and add to the list
                rankings.add(new PersonalRankingRow2(String.valueOf(rank), String.valueOf(departmentId), departmentName));
                rank++;
            }

            // Set the items in TableView
            departmentRankingTableView.setItems(rankings);

            db.disconnect();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleDepartmentRankingButton(ActionEvent actionEvent) {
        departmentsRanking();
    }

    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        HelloApplication change = new HelloApplication();
        change.changeSceneToHomeAdmin("homeadmin.fxml", userID);
    }
}
