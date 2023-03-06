package org.example.sql;

public class VacationSQL {
    public static final String GET_ALL_VACATION =
            "select e.name, v.*\n" +
            "from employee e\n" +
            "    inner  JOIN vacation v on e.id = v.employee_id;";
    public static final String GET_ONE_VACATION =
            "select e.name, v.*\n" +
            "from employee e\n" +
            "    inner  JOIN vacation v on e.id = v.employee_id\n" +
            "where v.id = ?;";
    public static final String INSERT_UPDATE_VACATION =
            "INSERT INTO vacation (id, employee_id, date, end_date, type) " +
            "VALUES (?, ?, ?, ?, ?);";
    public static final String DELETE_VACATION = "DELETE FROM vacation WHERE id = ?;";
}
