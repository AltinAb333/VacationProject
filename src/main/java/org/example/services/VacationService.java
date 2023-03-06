package org.example.services;

import org.example.domain.Vacation;
import org.example.domain.VacationRequest;

import java.util.ArrayList;

public interface VacationService {
    public ArrayList<Vacation> getVacation() throws Exception;
    public Vacation getOneVacation(String id) throws Exception;
    public void insertUpdateVacation(Vacation vacation) throws Exception;
    public void deleteVacation(String id) throws Exception;

}
