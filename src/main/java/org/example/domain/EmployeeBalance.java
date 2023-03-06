package org.example.domain;

import com.google.gson.annotations.SerializedName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class EmployeeBalance  {

    @SerializedName("id")
    private String id;
    @SerializedName("employee_id")
    private String employeeId;
    @SerializedName("balance")
    private int balance;
    @SerializedName("date")
    private Date date;
    @SerializedName("name")
    private String name;

    public EmployeeBalance(String id, String employeeId, int balance, Date date) {
        this.id = id;
        this.employeeId = employeeId;
        this.balance = balance;
        this.date = date;
    }

    public EmployeeBalance(ResultSet rs) throws SQLException {
        this.id = (rs.getString("id"));
        this.employeeId = rs.getString("employee_id");
        this.balance = rs.getInt("balance");
        this.date = rs.getDate("date");
        this.name = rs.getString("name");

    }

    public void populatePs(PreparedStatement ps) throws SQLException {
        if (this.getId() == null || this.getId().isEmpty()) {
            setId(UUID.randomUUID().toString());
        }
        ps.setString(1, this.getId());
        ps.setString(2, this.getEmployeeId());
        ps.setInt(3, this.getBalance());
        ps.setString(4, String.valueOf(new java.sql.Date(date.getTime())));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
