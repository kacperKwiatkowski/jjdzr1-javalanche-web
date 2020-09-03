package com.infoshareacademy.model;

import com.infoshareacademy.api.HolidayDate;
import com.infoshareacademy.api.Holidays;
import com.infoshareacademy.api.HolidaysJsonData;

import javax.ejb.Local;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.time.temporal.ChronoUnit.DAYS;

public class DayOff {
    private int id;
    private LocalDate startDay;
    private LocalDate endDay;
    private int idOfUser;
    private List<LocalDate> listOfDays = new ArrayList<>();

    public DayOff() {
    }

    public DayOff(int id, LocalDate startDay, LocalDate endDay, int idOfUser) {
        this.id = id;
        this.startDay = startDay;
        this.endDay = endDay;
        this.idOfUser = idOfUser;
        this.listOfDays = setListOfDays();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDate endDay) {
        this.endDay = endDay;
    }

    public int getIdOfUser() {
        return idOfUser;
    }

    public void setIdOfUser(int idOfUser) {
        this.idOfUser = idOfUser;
    }

    public List<LocalDate> getListOfDays() {
        return listOfDays;
    }

    public List<LocalDate> setListOfDays() {
        List<LocalDate> daysOffList = new ArrayList<>();
        LocalDate dayOfStart = this.startDay;
        LocalDate dayOfEnd = this.endDay;
        long daysBetween = DAYS.between(dayOfStart, dayOfEnd);

        for (long i = 0; i <= daysBetween; i++) {
            System.out.println(dayOfStart.plusDays(i));
            if(dayOfStart.plusDays(i).getDayOfWeek().toString().equalsIgnoreCase("saturday")
                    || dayOfStart.plusDays(i).getDayOfWeek().toString().equalsIgnoreCase("sunday"))
            {
                System.out.println("Is saturday or sunday");
            }
            else {
                daysOffList.add(dayOfStart.plusDays(i));
            }
        }
        return removeDayOffIfNationalHoliday(daysOffList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayOff)) return false;
        DayOff dayOff = (DayOff) o;
        return id == dayOff.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DayOff{" +
                "id=" + id +
                ", startDay=" + startDay +
                ", endDay=" + endDay +
                ", idOfUser=" + idOfUser +
                ", listOfDays=" + listOfDays +
                '}';
    }

    private List<LocalDate> removeDayOffIfNationalHoliday(List<LocalDate> daysOffList){

        for(int i = 0; i < daysOffList.size(); i++){
            for(int j = 0; j < nationalHolidaysParserToLocalDays().size(); j++){

                System.out.println(daysOffList.get(i).equals(nationalHolidaysParserToLocalDays().get(j)));
                if (daysOffList.get(i).equals(nationalHolidaysParserToLocalDays().get(j))){
                    daysOffList.remove(i);
                }
            }
        }

        return daysOffList;
    }

    private List<LocalDate> nationalHolidaysParserToLocalDays(){
        List<LocalDate> datesAaLocalDate = new ArrayList<>();
        List<Holidays> holidays = HolidaysJsonData.readDataFromJsonFile().getServerResponse().getHolidays();
        for(Holidays holiday : holidays){
            datesAaLocalDate.add(LocalDate.of(holiday.getHolidayDate().getHolidayDateTime().getYear(),
                                               holiday.getHolidayDate().getHolidayDateTime().getMonth(),
                                               holiday.getHolidayDate().getHolidayDateTime().getDay()));
        }
        return datesAaLocalDate;
    }
}
