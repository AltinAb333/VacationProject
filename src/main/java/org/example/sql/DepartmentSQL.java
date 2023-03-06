package org.example.sql;

public class DepartmentSQL {
    public static final String DELETE_DEPARTMENT = "DELETE FROM department WHERE id = ?;";
    public static final String INSERT_UPDATE_EMPLOYEE = "INSERT INTO department (id, name) VALUES (?, ?);";
    public static final String GET_ONE_DEPARTMENT = "SELECT * FROM department WHERE id = ?;";
    public static final String GET_ALL_DEPARTMENTS = "select * from department";
}
