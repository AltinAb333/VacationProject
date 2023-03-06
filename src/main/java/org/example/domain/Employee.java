package org.example.domain;

import com.google.gson.annotations.SerializedName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Employee {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("department")
    private Department department;
    private String departmentId;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    private String role = "USER";


    public Employee(ResultSet rs) throws SQLException {
        this.id = rs.getString("id");
        this.name = rs.getString("name");
        this.lastName = rs.getString("last_name");
        this.email = rs.getString("email");
        this.departmentId = rs.getString("department_id");
        this.username = rs.getString("username");
        this.password = rs.getString("password");
    }

    public Employee() {
    }

    private enum role {ADMIN, EMPLOYEE}


    public void populatePs(PreparedStatement ps) throws Exception {
        if (this.getId() == null || this.getId().isEmpty()) {
            setId(UUID.randomUUID().toString());
        }
        ps.setString(1, this.getId());
        ps.setString(2, this.getName());
        ps.setString(3, this.getLastName());
        ps.setString(4, this.getEmail());
        ps.setString(5, this.getDepartmentId());
        ps.setString(6, this.getUsername());
        ps.setString(7, this.getPassword());
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
