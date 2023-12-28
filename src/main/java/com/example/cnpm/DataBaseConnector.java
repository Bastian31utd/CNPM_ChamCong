package com.example.cnpm;

import java.sql.*;


public class DataBaseConnector {
    private static Connection connection;
    private final String databaseName;

    public DataBaseConnector() {
        this.databaseName = "database.db";
    }

    public void connect() {
        try {
            // Load SQLite JDBC driver
//            Class.forName("org.sqlite.JDBC");

            // Establish connection to the database
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);

            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to disconnect from the database: " + e.getMessage());
        }
    }

    /* Tra cuu ca lam viec cu the cua nhan vien*/
    public void getWorkScheduleForEmployee(int UsersId) {
        try {
            // Chuẩn bị truy vấn SQL
            String sql = "SELECT * FROM WorkSchedule WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, UsersId);

            // Thực hiện truy vấn và lấy kết quả
            ResultSet resultSet = statement.executeQuery();

            // Xử lý kết quả
            while (resultSet.next()) {
                int workScheduleId = resultSet.getInt("WorkScheduleID");
                int userId = resultSet.getInt("UserID");
                String workDate = resultSet.getString("WorkDate");
                String shift = resultSet.getString("Shift");

                // Xử lý thông tin lịch làm việc, ví dụ in ra console
                System.out.println("WorkScheduleID: " + workScheduleId + ", UserID: " + userId + ", WorkDate: " + workDate + ", Shift: " + shift);
            }
        } catch (SQLException e) {
            System.out.println("Truy vấn thất bại: " + e.getMessage());
        }
    }

    /*Xem thong tin ca lam viec */
    public void getAttendanceCountForShift(String date, String shift) {
        try {
            String query = "SELECT COUNT(*) AS present_count FROM WorkSchedule " +
                    "WHERE WorkDate = ? AND Shift = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, date);
            statement.setString(2, shift);

            ResultSet resultSet = statement.executeQuery();
            int presentCount = resultSet.getInt("present_count");

            System.out.println("Number of employees present in shift " + shift + " on " + date + ": " + presentCount);

            // Now, get the absent count
            int totalEmployees = getTotalEmployees();
            int absentCount = totalEmployees - presentCount;
            System.out.println("Number of employees absent in shift " + shift + " on " + date + ": " + absentCount);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* Tinh tong so nhan vien */
    private int getTotalEmployees() {
        try {
            String query = "SELECT COUNT(*) AS total_employees FROM Users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.getInt("total_employees");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* Xep hang nhan vien*/
    public void rankEmployees() {
        try {
            String query = "SELECT UserID, " +
                    "(SELECT COUNT(*) FROM Attendance WHERE UserID = Users.UserID AND Late = true) AS late_count, " +
                    "(SELECT COUNT(*) FROM LeaveRequest WHERE UserID = Users.UserID AND RequestType = 'Nghỉ phép' AND Status = 'Đã duyệt') AS leave_count, " +
                    "(SELECT COUNT(*) FROM LeaveRequest WHERE UserID = Users.UserID AND RequestType = 'Tăng ca' AND Status = 'Đã duyệt') AS overtime_count " +
                    "FROM Users";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int userId = resultSet.getInt("UserID");
                int lateCount = resultSet.getInt("late_count");
                int leaveCount = resultSet.getInt("leave_count");
                int overtimeCount = resultSet.getInt("overtime_count");

                // Calculate ranking based on criteria (you can define your own logic here)
                int totalPoints = lateCount * 2 + leaveCount * 5 + overtimeCount * 3;

                // You can store or print this information as needed
                System.out.println("UserID: " + userId + " - Total Points: " + totalPoints);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* Lấy RoleID của người dùng dựa trên UserID */
    public int getRoleID(String userID) {
        try {
            String query = "SELECT RoleID FROM Users WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userID);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt("RoleID");
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý lỗi theo ý bạn
            return -1;
        }
    }

    /* Kiểm tra thông tin đăng nhập */
    public String kiemTraDangNhap(String tenDangNhap, String matKhau) {
        try {
            // Chuẩn bị truy vấn SQL
            String sql = "SELECT UserID FROM Users WHERE Username = ? AND PasswordHash = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, tenDangNhap);
            statement.setString(2, matKhau);

            // Thực hiện truy vấn và lấy kết quả
            ResultSet resultSet = statement.executeQuery();

            // Kiểm tra xem tập kết quả có hàng nào không
            if (resultSet.next()) {
                // Người dùng được tìm thấy, trả về UserID
                String userId = resultSet.getString("UserID");
                return userId;
            } else {
                // Người dùng không được tìm thấy, trả về null
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*Truy vấn thông tin từ UserID*/
    public String getUserInfoByUserID(String userID, int queryType) {
        try {
            String columnName = ""; // Tên cột tương ứng với loại truy vấn

            // Xác định tên cột dựa trên loại truy vấn
            switch (queryType) {
                case 1:
                    columnName = "Name";
                    break;
                case 2:
                    columnName = "PhoneNumber";
                    break;
                case 3:
                    columnName = "Email";
                    break;
            }

            // Chuẩn bị truy vấn SQL để lấy thông tin từ UserID
            String sql = "SELECT " + columnName + " FROM Users WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userID);

            // Thực hiện truy vấn và lấy kết quả
            ResultSet resultSet = statement.executeQuery();
            // Lấy giá trị từ kết quả truy vấn
            return resultSet.getString(columnName);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    /*Xep hang phong ban*/
    public void calculateDepartmentRanking(int departmentId) {
        try {
            String query = "SELECT COUNT(*) AS EmployeeCount FROM Users WHERE DepartmentID = ?";
            PreparedStatement employeeCountStmt = connection.prepareStatement(query);
            employeeCountStmt.setInt(1, departmentId);
            ResultSet employeeCountResult = employeeCountStmt.executeQuery();

            int employeeCount = 0;
            if (employeeCountResult.next()) {
                employeeCount = employeeCountResult.getInt("EmployeeCount");
            }

            String performanceQuery = "SELECT SUM(Overtime) AS TotalOvertime, " +
                    "SUM(DaysOff) AS TotalDaysOff, " +
                    "SUM(Late) AS TotalLate " +
                    "FROM PerformanceData WHERE UserID IN " +
                    "(SELECT UserID FROM Users WHERE DepartmentID = ?)";
            PreparedStatement performanceStmt = connection.prepareStatement(performanceQuery);
            performanceStmt.setInt(1, departmentId);
            ResultSet performanceResult = performanceStmt.executeQuery();

            int totalOvertime = 0;
            int totalDaysOff = 0;
            int totalLate = 0;
            if (performanceResult.next()) {
                totalOvertime = performanceResult.getInt("TotalOvertime");
                totalDaysOff = performanceResult.getInt("TotalDaysOff");
                totalLate = performanceResult.getInt("TotalLate");
            }

            // Bây giờ bạn có thể tính toán xếp hạng của phòng/ban dựa trên các chỉ số hiệu suất đã tính được
            // Ví dụ:
            double departmentRanking = (totalOvertime + totalDaysOff + totalLate) / (double) employeeCount;

            System.out.println("Department Ranking for Department ID " + departmentId + ": " + departmentRanking);

            // Đóng các tài nguyên
            employeeCountResult.close();
            employeeCountStmt.close();
            performanceResult.close();
            performanceStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        DataBaseConnector connector = new DataBaseConnector();

        // Connect to the database
        connector.connect();

        // Disconnect from the database
        connector.disconnect();
    }

}