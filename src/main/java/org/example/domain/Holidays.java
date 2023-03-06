package org.example.domain;

import com.google.gson.annotations.SerializedName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class Holidays {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("date")
    private Date date;
    @SerializedName("description")
    private String description;

    public Holidays(String id, String name, Date date, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
    }

    public Holidays(ResultSet rs) throws SQLException {
        this.id = rs.getString("id");
        this.name = rs.getString("name");
        this.date = rs.getDate("date");
        this.description = rs.getString("description");
    }
    public void populatePs(PreparedStatement ps) throws Exception {
        java.sql.Date sqlPackageDate = new java.sql.Date(date.getTime());

        if (this.getId() == null || this.getId().isEmpty()) {
            setId(UUID.randomUUID().toString());
        }
        ps.setString(1, this.getId());
        ps.setString(2,this.getName());
        ps.setString(3, String.valueOf(sqlPackageDate));
        ps.setString(4,this.getDescription());
    }

    public Holidays() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
