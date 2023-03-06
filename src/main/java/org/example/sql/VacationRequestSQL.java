package org.example.sql;

public class VacationRequestSQL {
    public static final String GET_ALL_VACATION_REQUESTS =
            "select e.name, v.*\n" +
                    "from employee e\n" +
                    "    inner  JOIN vacation_request v on e.id = v.employee_id; ";
    public static final String GET_ONE_VACATION_REQUESTS =
            "select e.name, v.*\n" +
                    "from employee e\n" +
                    "    inner  JOIN vacation_request v on e.id = v.employee_id\n" +
                    "where v.id = ?;";
    public static final String INSERT_UPDATE_VACATION_REQUEST =
            "INSERT INTO vacation_request (id, start_date, end_date, type, employee_id, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    public static final String DELETE_VACATION_REQUEST = "DELETE FROM vacation_request WHERE id = ?;";
    public static final String APPROVE_STATUS =
            "update vacation_request\n" +
                    "set status = 'APPROVE'\n" +
                    "where id = ?;";
}
