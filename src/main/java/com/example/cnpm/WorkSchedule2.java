package com.example.cnpm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class WorkSchedule2 implements Initializable{
    @FXML
    private DatePicker datePicker;

    @FXML
    private ListView<String> workScheduleListView;

    private Connection connection;

    @FXML
    private void searchButtonClicked() {
        LocalDate selectedDate = datePicker.getValue();
        String formattedDate = selectedDate.format(DateTimeFormatter.ISO_DATE);
        List<String> workSchedules = getWorkSchedulesForDate(formattedDate);
        workScheduleListView.getItems().setAll(workSchedules);
    }

    private List<String> getWorkSchedulesForDate(String selectedDate) {
        List<String> schedules = new ArrayList<>();
        try {
            String databaseName = "database.db";
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);

            String sql = "SELECT WorkDate, Shift FROM WorkSchedule WHERE WorkDate = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, selectedDate);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String workDate = resultSet.getString("WorkDate");
                String shift = resultSet.getString("Shift");
                schedules.add("WorkDate: " + workDate + ", Shift: " + shift);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize logic
    }

    public void backButtonClicked(ActionEvent actionEvent) {
    }
}
