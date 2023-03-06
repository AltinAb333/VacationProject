package org.example.services;

import org.example.domain.VacationRequest;

import java.sql.Connection;
import java.util.ArrayList;

public interface VacationRequestService {
    ArrayList<VacationRequest> getVacationRequests() throws Exception;

    VacationRequest getOneVacationRequest(String id) throws Exception;

    void insertUpdateVacationRequest(VacationRequest vacationRequest) throws Exception;

    void deleteVacationRequest(String id, Connection con) throws Exception;
}
