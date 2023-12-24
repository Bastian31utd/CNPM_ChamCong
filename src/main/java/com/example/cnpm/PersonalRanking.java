package com.example.cnpm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.sql.*;

public class PersonalRanking {
    @FXML
    private ListView<String> rankingListView;
    @FXML
    public void backButtonClicked(ActionEvent actionEvent) {
    }

    @FXML
    public void initialize() {
        // Gọi phương thức để thực hiện việc xếp hạng
        rankEmployees();
    }

    @FXML
    private void handleSortButton(ActionEvent event) {
        // Gọi phương thức để thực hiện việc xếp hạng khi nút sắp xếp được nhấn
        rankEmployees();
    }

    private void rankEmployees() {
        try {
            // Kết nối đến cơ sở dữ liệu
            String databaseName = "database.db";
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);

            String query = "SELECT u.UserID, u.UserName, " +
                    "(SELECT COUNT(*) FROM Attendance WHERE UserID = u.UserID AND Late = true) AS late_count, " +
                    "(SELECT COUNT(*) FROM LeaveRequest WHERE UserID = u.UserID AND RequestType = 'Nghỉ phép' AND Status = 'Cofirm') AS leave_count, " +
                    "(SELECT COUNT(*) FROM LeaveRequest WHERE UserID = u.UserID AND RequestType = 'Tăng ca' AND Status = 'Confirm') AS overtime_count " +
                    "FROM Users u";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Create a StringBuilder to store rankings
            StringBuilder rankings = new StringBuilder();

            while (resultSet.next()) {
                int userId = resultSet.getInt("UserID");
                String userName = resultSet.getString("UserName");
                int lateCount = resultSet.getInt("late_count");
                int leaveCount = resultSet.getInt("leave_count");
                int overtimeCount = resultSet.getInt("overtime_count");

                // Calculate ranking based on your criteria
                int totalPoints = -lateCount * 2 - leaveCount * 3 + overtimeCount * 5;

                // Append information to the rankings string
                rankings.append("UserID: ").append(userId).append(" - UserName: ").append(userName).append(" - Total Points: ").append(totalPoints).append("\n");
            }

            // Clear previous items in the ListView
            rankingListView.getItems().clear();

            // Display the rankings in the ListView
            rankingListView.getItems().add(rankings.toString());

            // Đóng kết nối sau khi hoàn thành công việc với cơ sở dữ liệu
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
//Xếp hạng phòng,ban
    @FXML
    private void handleDepartmentRankingButton(ActionEvent event) {
        departmentsRanking();
    }

    private void departmentsRanking() {
        try {
            // Kết nối đến cơ sở dữ liệu
            String databaseName = "database.db";
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);

            String query = "SELECT DepartmentID, DepartmentName, AVG(totalPoints) as AveragePoints " +
                    "FROM (" +
                    "    SELECT u.DepartmentID, " +
                    "           (SELECT COUNT(*) FROM Attendance WHERE UserID = u.UserID AND Late = true) AS late_count, " +
                    "           (SELECT COUNT(*) FROM LeaveRequest WHERE UserID = u.UserID AND RequestType = 'Nghỉ phép' AND Status = 'Đã duyệt') AS leave_count, " +
                    "           (SELECT COUNT(*) FROM LeaveRequest WHERE UserID = u.UserID AND RequestType = 'Tăng ca' AND Status = 'Đã duyệt') AS overtime_count, " +
                    "           (SELECT COUNT(*) FROM Users) AS total_users " +
                    "    FROM Users u" +
                    ") " +
                    "GROUP BY DepartmentID";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Create a StringBuilder to store department rankings
            StringBuilder departmentRankings = new StringBuilder();

            while (resultSet.next()) {
                int departmentId = resultSet.getInt("DepartmentID");
                String departmentName = resultSet.getString("DepartmentName");
                double averagePoints = resultSet.getDouble("AveragePoints");

                // Append information to the department rankings string
                departmentRankings.append("DepartmentID: ").append(departmentId)
                        .append(" - DepartmentName: ").append(departmentName)
                        .append(" - Average Points: ").append(averagePoints).append("\n");
            }

            // Clear previous items in the ListView
            rankingListView.getItems().clear();

            // Display the department rankings in the ListView
            rankingListView.getItems().add(departmentRankings.toString());

            // Đóng kết nối sau khi hoàn thành công việc với cơ sở dữ liệu
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}

