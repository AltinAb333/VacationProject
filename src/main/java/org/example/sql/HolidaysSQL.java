package org.example.sql;

public class HolidaysSQL {
    public static final String GET_ALL_HOLIDAYS = "SELECT * FROM holidays;";
    public static final String GET_ONE_HOLIDAY = "SELECT * FROM holidays WHERE id = ?;";
    public static final String INSERT_UPDATE_HOLIDAY = "INSERT INTO holidays (id, name, date, description) VALUES (?, ?, ?, ?);";
    public static final String DELETE_HOLIDAY = "DELETE FROM holidays WHERE id = ?;";
}
