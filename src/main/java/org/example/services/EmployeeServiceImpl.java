package org.example.services;

import org.example.Database;
import org.example.common.Service;
import org.example.domain.Employee;
import org.example.domain.EmployeeBalance;
import org.example.sql.EmployeeSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class EmployeeServiceImpl extends Service implements EmployeeService {
    @Override
    public ArrayList<Employee> getEmployees() throws Exception {
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(EmployeeSQL.GET_ALL_EMPLOYEES);
            rs = ps.executeQuery();
            while (rs.next()) {
                employeeArrayList.add(new Employee(rs));
            }
            return employeeArrayList;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }

    @Override
    public ArrayList<Employee> getOneEmployee(String id) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = new Database().getConnection();
            ArrayList<Employee> employeeArrayList = new ArrayList<>();
            ps = con.prepareStatement(EmployeeSQL.GET_ONE_EMPLOYEE);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                employeeArrayList.add(new Employee(rs));
            }
            return employeeArrayList;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }

    @Override
    public void insertUpdateEmployee(Employee employee) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(EmployeeSQL.INSERT_UPDATE_EMPLOYEE);
            employee.populatePs(ps);
            ps.execute();
        } finally {
            closePreparedStatement(ps);
            closeConnection(con);
        }
        EmployeeBalance employeeBalance = new EmployeeBalance(UUID.randomUUID().toString(), employee.getId(), 12, new Date());
        new EmployeeBalanceServiceImpl().insertUpdateEmployeeBalance(employeeBalance);
    }

    @Override
    public void deleteEmployee(String id) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(EmployeeSQL.DELETE_EMPLOYEE);
            ps.setString(1, id);
            ps.execute();
        } finally {
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }

}
