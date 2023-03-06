package org.example.services;

import org.example.Database;
import org.example.common.Service;
import org.example.domain.EmployeeBalance;
import org.example.domain.Vacation;
import org.example.domain.VacationRequest;
import org.example.sql.EmployeeBalanceSQL;
import org.example.sql.VacationSQL;

import javax.ws.rs.BadRequestException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VacationServiceImpl extends Service implements VacationService {
    @Override
    public ArrayList<Vacation> getVacation() throws Exception {
        ArrayList<Vacation> vacationList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(VacationSQL.GET_ALL_VACATION);
            rs = ps.executeQuery();
            while (rs.next()) {
                vacationList.add(new Vacation(rs));
            }
            return vacationList;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }
    @Override
    public Vacation getOneVacation(String id) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(VacationSQL.GET_ONE_VACATION);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Vacation(rs);
            }

        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
        return null;
    }

    @Override
    public void insertUpdateVacation(Vacation vacation) throws Exception {
        String employeeID = vacation.getEmployeeId();
        EmployeeBalance employeeBalance = new EmployeeBalanceServiceImpl().getEmployeeBalanceByEmployeeId(employeeID);
        new VacationServiceImpl().hasBalanceLeft(vacation, employeeBalance);
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(VacationSQL.INSERT_UPDATE_VACATION);
            vacation.populatePs(ps);
            ps.execute();
        } finally {
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }

    @Override
    public void deleteVacation(String id) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(VacationSQL.DELETE_VACATION);
            ps.setString(1, id);
            ps.execute();
        } finally {
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }
    public void hasBalanceLeft(Vacation vacation, EmployeeBalance employeeBalance) {
        int dateDifference = (int) (Math.abs(vacation.getStartDate().getTime() - vacation.getEndDate().getTime())) / (86400000);
        if (employeeBalance.getBalance() < dateDifference) {
            throw new BadRequestException("There is no balance left");
        }
    }


}
