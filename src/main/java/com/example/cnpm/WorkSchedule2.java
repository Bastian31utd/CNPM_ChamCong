package com.example.cnpm;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WorkSchedule2 implements Initializable {
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> shiftComboBox;


    @FXML
    public void backButtonClicked(ActionEvent actionEvent) {
    }

    @FXML
    private TableView<WorkScheduleRow2> workScheduleTableView;

    @FXML
    private TableColumn<WorkScheduleRow2, String> rankColumn;

    @FXML
    private TableColumn<WorkScheduleRow2, String> userIDColumn;

    @FXML
    private TableColumn<WorkScheduleRow2, String> employeeNameColumn;


    DataBaseConnector db=new DataBaseConnector();

    @FXML
    private void searchButtonClicked() {
        LocalDate selectedDate = datePicker.getValue();
        String formattedDate = selectedDate.format(DateTimeFormatter.ISO_DATE);
        String selectedShift = shiftComboBox.getValue(); // Lấy giá trị từ ComboBox

        // Kiểm tra xem ngày và ca làm việc đã được chọn chưa
        if (selectedDate != null && selectedShift != null) {
            ObservableList<WorkScheduleRow2> workSchedules = getWorkSchedulesForDateAndShift(formattedDate, selectedShift);
            workScheduleTableView.setItems(workSchedules);
        }
    }


    private ObservableList<WorkScheduleRow2> getWorkSchedulesForDateAndShift(String selectedDate, String selectedShift) {
        ObservableList<WorkScheduleRow2> schedules = FXCollections.observableArrayList();
        try {
            db.connect();

            String sql = "SELECT WorkSchedule.WorkDate, WorkSchedule.Shift, Users.UserID, Users.Name " +
                    "FROM WorkSchedule " +
                    "JOIN Users ON WorkSchedule.UserID = Users.UserID " +
                    "WHERE WorkSchedule.WorkDate = ? AND WorkSchedule.Shift = ?";

            PreparedStatement statement = db.getConnection().prepareStatement(sql);
            statement.setString(1, selectedDate);
            statement.setString(2, selectedShift);

            ResultSet resultSet = statement.executeQuery();

            int rank = 1;
            while (resultSet.next()) {
                String workDate = resultSet.getString("WorkDate");
                String shift = resultSet.getString("Shift");
                String userId = resultSet.getString("UserID");
                String userName = resultSet.getString("Name");
                schedules.add(new WorkScheduleRow2(String.valueOf(rank), userId, userName));
                rank++;
            }
            db.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> shiftOptions = FXCollections.observableArrayList("Sáng", "Chiều");
        shiftComboBox.setItems(shiftOptions);

        rankColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getrank()));
        userIDColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getuserID()));
        employeeNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmployeeName()));

        // Initialize table view with empty data or perform other initialization tasks
        workScheduleTableView.setItems(FXCollections.observableArrayList());
    }

}
