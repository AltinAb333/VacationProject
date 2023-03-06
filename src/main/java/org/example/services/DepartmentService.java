package org.example.services;

import org.example.domain.Department;

import java.util.ArrayList;

public interface DepartmentService {
    public ArrayList<Department> getDepartment() throws Exception;
    public ArrayList<Department> getOneDepartment(String id) throws Exception;
    public void insertUpdateDepartment(Department department) throws Exception;
    public void deleteDepartment(String id) throws Exception;
}

