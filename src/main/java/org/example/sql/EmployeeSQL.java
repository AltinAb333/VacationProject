package org.example.sql;

public class EmployeeSQL {
    public static final String GET_ALL_EMPLOYEES = "SELECT * FROM employee";
    public static final String GET_ONE_EMPLOYEE = "SELECT * FROM employee WHERE id = ?;";
    public static final String INSERT_UPDATE_EMPLOYEE = "INSERT INTO employee (id, name, last_name, email, department_id, username, password) VALUES (?, ?, ?, ?, ?, ?, ?);";
    public static final String DELETE_EMPLOYEE = "DELETE FROM holidays WHERE id = ?;";
}
