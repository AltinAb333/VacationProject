package org.example.domain;


import com.google.gson.annotations.SerializedName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class Vacation {
    @SerializedName("id")
    private String id;
    @SerializedName("employee_id")
    private String employeeId;
    @SerializedName("start_date")
    private Date startDate;
    @SerializedName("end_date")
    private Date endDate;
    @SerializedName("request_date")
    private Date requestDate;
    public enum type {NORMAL,WITHOUT_PAY,SICK}
    @SerializedName("type")
    private String type;
    @SerializedName("name")
    private String name;


    public Vacation(ResultSet rs) throws SQLException {
        this.id = rs.getString("id");
        this.employeeId =  rs.getString("employee_id");
        this.startDate = rs.getDate(("date"));
        this.endDate = rs.getDate("end_date");
        this.type=  rs.getString("type");
        this.name = rs.getString("name");
    }

    public void populatePs(PreparedStatement ps) throws Exception {
        if (this.getId() == null || this.getId().isEmpty()) {
            setId(UUID.randomUUID().toString());
        }
        ps.setString(1, this.getId());
        ps.setString(2, this.getEmployeeId());
        ps.setString(3, String.valueOf(new java.sql.Date(startDate.getTime())));
        ps.setString(4, String.valueOf(new java.sql.Date(endDate.getTime())));
        ps.setString(5,this.type);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
