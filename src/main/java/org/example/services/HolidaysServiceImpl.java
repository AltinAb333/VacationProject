package org.example.services;

import org.example.Database;
import org.example.common.Service;
import org.example.domain.Holidays;
import org.example.sql.HolidaysSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HolidaysServiceImpl extends Service implements HolidaysService {
    @Override
    public ArrayList<Holidays> getHolidays() throws Exception{
        ArrayList<Holidays> holidaysArrayList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(HolidaysSQL.GET_ALL_HOLIDAYS);
            rs = ps.executeQuery();
            while (rs.next()) {
                holidaysArrayList.add(new Holidays(rs));
            }
            return holidaysArrayList;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }
    @Override
    public ArrayList<Holidays> getOneHoliday(String id) throws Exception{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = new Database().getConnection();
            ArrayList<Holidays> holidaysArrayList = new ArrayList<>();
            ps = con.prepareStatement(HolidaysSQL.GET_ONE_HOLIDAY);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                holidaysArrayList.add(new Holidays(rs));
            }
            return holidaysArrayList;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }
    @Override
    public void insertUpdateHoliday(Holidays holidays) throws Exception{
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(HolidaysSQL.INSERT_UPDATE_HOLIDAY);
            holidays.populatePs(ps);
            ps.execute();
        } finally {
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }
    @Override
    public void deleteHoliday(String id) throws Exception{
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(HolidaysSQL.DELETE_HOLIDAY);
            ps.setString(1, id);
            ps.execute();
        } finally {
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }
}
