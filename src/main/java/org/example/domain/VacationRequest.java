package org.example.domain;

import com.google.gson.annotations.SerializedName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class VacationRequest {
    @SerializedName("id")
    private String id;
    @SerializedName("start_date")
    private Date startDate;
    @SerializedName("end_date")
    private Date endDate;
    @SerializedName("status")
    private String status;
    @SerializedName("employee_id")
    private String employeeId;
    @SerializedName("type")
    private String type;
    @SerializedName("name")
    private String name;

    public enum type {NORMAL, WITHOUT_PAY, SICK}

    public VacationRequest(ResultSet rs) throws SQLException {
        this.id = rs.getString("id");
        this.startDate = rs.getDate("start_date");
        this.endDate = rs.getDate("end_date");
        this.status = rs.getString("status");
        this.employeeId = rs.getString("employee_id");
        this.type = rs.getString("type");
        this.name = rs.getString("name");

    }

    public void populatePs(PreparedStatement ps) throws Exception {
        if (this.getId() == null || this.getId().isEmpty()) {
            setId(UUID.randomUUID().toString());
        }
        ps.setString(1, this.getId());
        ps.setString(2, String.valueOf(new java.sql.Date(startDate.getTime())));
        ps.setString(3, String.valueOf(new java.sql.Date(endDate.getTime())));
        ps.setString(4, this.type);
        ps.setString(5, this.employeeId);
        ps.setString(6, this.status);
    }

    public void approveRequest() {
        if (this.status.toLowerCase() == "pending") {
            this.status = "approved";
        } else {
            throw new IllegalStateException("Cannot approve a request that is not pending.");
        }
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
