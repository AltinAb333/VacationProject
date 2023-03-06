package org.example.services;

import org.example.Database;
import org.example.common.Service;
import org.example.domain.EmployeeBalance;
import org.example.domain.Holidays;
import org.example.domain.VacationRequest;
import org.example.sql.EmployeeBalanceSQL;
import org.example.sql.VacationRequestSQL;
import org.example.sql.VacationSQL;

import javax.ws.rs.BadRequestException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VacationRequestServiceImpl extends Service implements VacationRequestService {

    @Override
    public ArrayList<VacationRequest> getVacationRequests() throws Exception {
        ArrayList<VacationRequest> vacationRequestList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(VacationRequestSQL.GET_ALL_VACATION_REQUESTS);
            rs = ps.executeQuery();
            while (rs.next()) {
                vacationRequestList.add(new VacationRequest(rs));
            }
            return vacationRequestList;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }

    @Override
    public VacationRequest getOneVacationRequest(String id) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(VacationRequestSQL.GET_ONE_VACATION_REQUESTS);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new VacationRequest(rs);
            }
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
        return null;
    }

    @Override
    public void insertUpdateVacationRequest(VacationRequest vacationRequest) throws Exception {
        EmployeeBalance employeeBalance = new EmployeeBalanceServiceImpl().getEmployeeBalanceByEmployeeId(vacationRequest.getEmployeeId());
        if (employeeBalance == null) {
            throw new BadRequestException("The employee with id: " +
                    vacationRequest.getEmployeeId() + " doesn't have a balance");
        }

        new VacationRequestServiceImpl().hasBalanceLeft(vacationRequest, employeeBalance);
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(VacationRequestSQL.INSERT_UPDATE_VACATION_REQUEST);
            vacationRequest.populatePs(ps);
            ps.execute();
        } finally {
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }

    @Override
    public void deleteVacationRequest(String id, Connection con) throws Exception {
        Connection connection;
        if (con == null) {
            connection = new Database().getConnection();
        } else {
            connection = con;
        }
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(VacationRequestSQL.DELETE_VACATION_REQUEST);
            ps.setString(1, id);
            ps.execute();
        } finally {
            closePreparedStatement(ps);
            if (con == null) {
                closeConnection(connection);
            }
        }
    }

    public void hasBalanceLeft(VacationRequest vacationRequest, EmployeeBalance employeeBalance) {
        int dateDifference = (int) (Math.abs(vacationRequest.getStartDate().getTime() - vacationRequest.getEndDate().getTime())) / (1000 * 60 * 60 * 24);
        if (employeeBalance.getBalance() < dateDifference) {
            throw new BadRequestException("There is no balance left");
        }
    }

    public void calculateBalance(VacationRequest vacationRequest, EmployeeBalance employeeBalance, Connection con) throws Exception {
        ArrayList<Holidays> holidays = new HolidaysServiceImpl().getHolidays();
        PreparedStatement ps = null;

        int temp = 0;
        Calendar cal = Calendar.getInstance();
        Calendar holidayCal = Calendar.getInstance();
        hasBalanceLeft(vacationRequest, employeeBalance);

        for (Date date = vacationRequest.getStartDate();
             date.before(vacationRequest.getEndDate()); date = cal.getTime()) {
            cal.setTime(date);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            } else {
                temp++;
            }
            cal.add(Calendar.DATE, 1);
        }
        for (Holidays holidays1 : holidays) {
            Date holidayDate = holidays1.getDate();
            holidayCal.setTime(holidayDate);

            for (Date date = vacationRequest.getStartDate();
                 date.before(vacationRequest.getEndDate()); date = cal.getTime()) {
                cal.setTime(date);
                int test = holidayCal.get(Calendar.DATE);
                int dayOfWeek = cal.get(Calendar.DATE);
                if (dayOfWeek == Calendar.SATURDAY) {
                } else if (dayOfWeek == test) {
                    temp--;
                }
                cal.add(Calendar.DATE, 1);
            }

        }

        int balance = employeeBalance.getBalance();
        balance = balance - temp;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(EmployeeBalanceSQL.UPDATE_BALANCE);
            ps.setInt(1, balance);
            ps.setString(2, vacationRequest.getEmployeeId());
            ps.executeUpdate();
        } finally {
            ps.close();
        }

    }

    public void approveStatus(String id) throws Exception {
        VacationRequest vacationRequest = new VacationRequestServiceImpl().getOneVacationRequest(id);
        if (vacationRequest == null) {
            throw new BadRequestException("Vacation request not found!");
        }
        EmployeeBalance employeeBalance = new EmployeeBalanceServiceImpl().getEmployeeBalanceByEmployeeId(vacationRequest.getEmployeeId());
        if (vacationRequest.getStatus().equals("APPROVE")) {
            throw new BadRequestException("Vacation request already approved!");
        }
        doApproveStatus(vacationRequest, employeeBalance);
    }

    private void doApproveStatus(VacationRequest vacationRequest, EmployeeBalance employeeBalance) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(VacationRequestSQL.APPROVE_STATUS);
            ps.setString(1, vacationRequest.getId());
            ps.executeUpdate();

            calculateBalance(vacationRequest, employeeBalance, con);

            ps2 = con.prepareStatement(VacationSQL.INSERT_UPDATE_VACATION);
            ps2.setString(1, vacationRequest.getId());
            ps2.setString(2, vacationRequest.getEmployeeId());
            ps2.setString(3, String.valueOf(new java.sql.Date(vacationRequest.getStartDate().getTime())));
            ps2.setString(4, String.valueOf(new java.sql.Date(vacationRequest.getEndDate().getTime())));
            ps2.setString(5, vacationRequest.getType());
            ps2.executeUpdate();
            new VacationRequestServiceImpl().deleteVacationRequest(vacationRequest.getId(), con);

        } finally {
            closePreparedStatement(ps, ps2);
            closeConnection(con);
        }
    }


}
