package org.example.services;


import org.example.domain.EmployeeBalance;

import java.util.ArrayList;

public interface EmployeeBalanceService {
    ArrayList<EmployeeBalance> getEmployeeBalances() throws Exception;


    EmployeeBalance getEmployeeBalanceByEmployeeId(String id) throws Exception;

    void insertUpdateEmployeeBalance(EmployeeBalance employeeBalance) throws Exception;

    void deleteEmployeeBalance(String id) throws Exception;
}
