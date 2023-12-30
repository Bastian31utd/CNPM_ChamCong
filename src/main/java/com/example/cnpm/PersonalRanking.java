package com.example.cnpm;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PersonalRanking implements Initializable {
    @FXML
    private TableView<PersonalRankingRow> personalRankingTableView;

    @FXML
    private TableColumn<PersonalRankingRow, String> rankColumn;

    @FXML
    private TableColumn<PersonalRankingRow, String> userIDColumn;

    @FXML
    private TableColumn<PersonalRankingRow, String> nameColumn;
    @FXML
    public void backButtonClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void initialize() {
        // Gọi phương thức để thực hiện việc xếp hạng
        rankEmployees();
    }
    DataBaseConnector db=new DataBaseConnector();
    @FXML
    private void handleSortButton(ActionEvent event) {
        // Gọi phương thức để thực hiện việc xếp hạng khi nút sắp xếp được nhấn
        rankEmployees();
    }

    private void rankEmployees() {
        try {
            db.connect();
            String query = "SELECT u.UserID, u.Name, " +
                    "(SELECT COUNT(*) FROM Attendance WHERE UserID = u.UserID AND Late = true) AS late_count, " +
                    "(SELECT COUNT(*) FROM LeaveRequest WHERE UserID = u.UserID AND RequestType = 'Nghỉ phép' AND Status = 'Confirm') AS leave_count, " +
                    "(SELECT COUNT(*) FROM LeaveRequest WHERE UserID = u.UserID AND RequestType = 'Tăng ca' AND Status = 'Confirm') AS overtime_count " +
                    "FROM Users u";

            PreparedStatement statement = db.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            ObservableList<PersonalRankingRow> rankings = FXCollections.observableArrayList();

            int rank = 1;
            while (resultSet.next()) {
                String userId = resultSet.getString("UserID");
                String name = resultSet.getString("Name");
                int lateCount = resultSet.getInt("late_count");
                int leaveCount = resultSet.getInt("leave_count");
                int overtimeCount = resultSet.getInt("overtime_count");

                // Calculate ranking based on your criteria
                int totalPoints = -lateCount * 2 - leaveCount * 3 + overtimeCount * 5;

                // Create PersonalRankingRow object and add to the list
                rankings.add(new PersonalRankingRow(String.valueOf(rank), userId, name));
                rank++;
            }

            // Clear previous items in the TableView
            personalRankingTableView.getItems().clear();

            // Set the items in TableView
            personalRankingTableView.setItems(rankings);

            db.disconnect();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*Xếp hạng phòng,ban
    @FXML
    private void handleDepartmentRankingButton(ActionEvent event) {
        departmentsRanking();
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


                // Create a StringBuilder to store department rankings
                StringBuilder departmentRankings = new StringBuilder();

                while (resultSet.next()) {
                    int departmentId = resultSet.getInt("DepartmentID");
                    String departmentName = resultSet.getString("DepartmentName");
                    int totalPoints = resultSet.getInt("TotalPoints");
                    int employeeCount = resultSet.getInt("EmployeeCount");

                    // Calculate average points
                    double averagePoints = (double) totalPoints / employeeCount;

                    // Append information to the department rankings string
                    departmentRankings.append("DepartmentID: ").append(departmentId)
                            .append(" - DepartmentName: ").append(departmentName)
                            .append("\n");
                }


            // Clear previous items in the ListView
            rankingListView.getItems().clear();

            // Display the department rankings in the ListView
            rankingListView.getItems().add(departmentRankings.toString());

            db.disconnect();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rankColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getrank()));
        userIDColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getuserID()));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getname()));

    }
}

