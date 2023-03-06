package org.example.services;

import org.example.Database;
import org.example.common.Service;
import org.example.domain.Department;
import org.example.sql.DepartmentSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DepartmentServiceImpl extends Service implements DepartmentService {
    @Override
    public ArrayList<Department> getDepartment() throws Exception{
        ArrayList<Department> departmentArrayList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(DepartmentSQL.GET_ALL_DEPARTMENTS);
            rs = ps.executeQuery();
            while (rs.next()) {
                departmentArrayList.add(new Department(rs));
            }
            return departmentArrayList;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }
    @Override
    public ArrayList<Department> getOneDepartment(String id) throws Exception{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = new Database().getConnection();
            ArrayList<Department> departmentArrayList = new ArrayList<>();
            ps = con.prepareStatement(DepartmentSQL.GET_ONE_DEPARTMENT);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                departmentArrayList.add(new Department(rs));
            }
            return departmentArrayList;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }
    @Override
    public void insertUpdateDepartment(Department department) throws Exception{
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(DepartmentSQL.INSERT_UPDATE_EMPLOYEE);
            department.populatePs(ps);
            ps.execute();
        } finally {
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }
    @Override
    public void deleteDepartment(String id) throws Exception{
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = new Database().getConnection();
            ps = con.prepareStatement(DepartmentSQL.DELETE_DEPARTMENT);
            ps.setString(1, id);
            ps.execute();
        } finally {
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }

}
