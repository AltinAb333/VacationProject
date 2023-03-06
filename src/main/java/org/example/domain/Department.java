package org.example.domain;

import com.google.gson.annotations.SerializedName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Department {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public Department(String departmentId, String getDepartmentName) {
        this.id = departmentId;
        this.name = getDepartmentName;
    }

    public Department(ResultSet rs) throws SQLException {
        this.id = rs.getString("id");
        this.name = rs.getString("name");
    }
    public void populatePs(PreparedStatement ps) throws Exception {

        if (this.getId() == null || this.getId().isEmpty()) {
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2,this.getName());
        }
        ps.setString(1, this.getId());
        ps.setString(2,this.getName());
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
}
