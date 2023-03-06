package org.example.services;

import org.example.Database;
import org.example.common.Service;
import org.example.domain.EmployeeBalance;
import org.example.sql.EmployeeBalanceSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EmployeeBalanceServiceImpl extends Service implements EmployeeBalanceService {
    @Override
    public ArrayList<EmployeeBalance> getEmployeeBalances() throws Exception {
        ArrayList<EmployeeBalance> employeeBalanceArrayList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(EmployeeBalanceSQL.GET_ALL_EMPLOYEE_BALANCES);
            rs = ps.executeQuery();
            while (rs.next()) {
                employeeBalanceArrayList.add(new EmployeeBalance(rs));
            }
            return employeeBalanceArrayList;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }

    @Override
    public EmployeeBalance getEmployeeBalanceByEmployeeId(String id) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(EmployeeBalanceSQL.GET_ONE_EMPLOYEE_BALANCE_BY_EMPLOYEE_ID);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new EmployeeBalance(rs);
            }
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
        return null;
    }

    @Override
    public void insertUpdateEmployeeBalance(EmployeeBalance employeeBalance) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(EmployeeBalanceSQL.INSERT_UPDATE_EMPLOYEE_BALANCE);
            employeeBalance.populatePs(ps);
            ps.execute();
        } finally {
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }

    @Override
    public void deleteEmployeeBalance(String id) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(EmployeeBalanceSQL.DELETE_EMPLOYEE_BALANCE);
            ps.setString(1, id);
            ps.execute();
        } finally {
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }
}
