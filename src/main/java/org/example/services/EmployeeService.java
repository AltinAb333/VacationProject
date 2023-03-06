package org.example.services;

import org.example.domain.Employee;

import java.util.ArrayList;

public interface EmployeeService {
    public ArrayList<Employee> getEmployees() throws Exception;
    public ArrayList<Employee> getOneEmployee(String id) throws Exception;
    public void insertUpdateEmployee(Employee employee) throws Exception;
    public void deleteEmployee(String id) throws Exception;
}
