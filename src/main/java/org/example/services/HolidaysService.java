package org.example.services;

import org.example.domain .Holidays;

import java.util.ArrayList;

public interface HolidaysService {
    public ArrayList<Holidays> getHolidays() throws Exception;
    public ArrayList<Holidays> getOneHoliday(String id) throws Exception;
    public void insertUpdateHoliday(Holidays holidays) throws Exception;
    public void deleteHoliday(String id) throws Exception;
}
