package org.example.sql;

public class EmployeeBalanceSQL {
    public static final String DELETE_EMPLOYEE_BALANCE = "DELETE FROM employee_balances WHERE id = ?;";
    public static final String INSERT_UPDATE_EMPLOYEE_BALANCE = "INSERT INTO employee_balances (id, employee_id, balance, date) VALUES (?, ?, ?, ?);";
    public static final String GET_ONE_EMPLOYEE_BALANCE = "SELECT * FROM employee_balances WHERE id = ?;";
    public static final String GET_ALL_EMPLOYEE_BALANCES =
            "select e.name, em.*\n" +
            "from employee e\n" +
            "    inner  JOIN employee_balances em on e.id = em.employee_id;" ;

    public static final String GET_ONE_EMPLOYEE_BALANCE_BY_EMPLOYEE_ID =
            "SELECT e.*, eb.*\n" +
            "FROM employee e\n" +
            "        inner JOIN employee_balances eb ON e.id = eb.employee_id\n" +
            "where employee_id = ?;";

    public static final String UPDATE_BALANCE =
            "update employee_balances\n" +
            "SET balance = ?\n" +
            "where employee_id = ?;";
}
