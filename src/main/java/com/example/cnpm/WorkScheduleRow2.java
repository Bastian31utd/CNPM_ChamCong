package com.example.cnpm;
import javafx.beans.property.SimpleStringProperty;
public class WorkScheduleRow2 {
    private final SimpleStringProperty rank;
    private final SimpleStringProperty userID;
    private final SimpleStringProperty employeeName;

    public WorkScheduleRow2(String rank, String userID, String employeeName) {
        this.rank = new SimpleStringProperty(rank);
        this.userID = new SimpleStringProperty(userID);
        this.employeeName = new SimpleStringProperty(employeeName);
    }

    public String getrank() {
        return rank.get();
    }

    public String getuserID() {
        return userID.get();
    }

    public String getEmployeeName() {
        return employeeName.get();
    }
}
