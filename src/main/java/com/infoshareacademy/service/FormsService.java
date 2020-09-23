package com.infoshareacademy.service;


import com.infoshareacademy.model.DayOff;
import com.infoshareacademy.model.Team;
import com.infoshareacademy.model.User;
import com.infoshareacademy.repository.DayOffRepository;
import com.infoshareacademy.repository.TeamRepository;
import com.infoshareacademy.repository.UserRepository;

import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;

@LocalBean
@Transactional
public class FormsService {


    @Inject
    private UserRepository userRepository;

    @Inject
    private DayOffRepository dayOffRepository;

    @Inject
    private TeamRepository teamRepository;

    @Inject
    private DayOffService dayOffService;


    public int loggedUsersLevelOfAccessRetriever(String email){
        return userRepository.findByEmail(email).getLevelOfAccess();
    }

    public void addUserFormInputDatabaseHandler(String username, String password, String firstName, String lastname, int daysOff, int levelOfAccess){
        User user = new User();
        user.setEmail(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastname);
        user.setDaysOffLeft(daysOff);
        user.setLevelOfAccess(levelOfAccess);
        userRepository.create(user);
    }

    public void deleteUserFormInputHandler(int recordToDeleteId){
        userRepository.delete(userRepository.findById(recordToDeleteId));
    }

    public void addTeamFormInputHandler(String teamName, String teamLeaderUsername){
        Team team = new Team();
        team.setName(teamName);
        team.setTeamLeader(userRepository.findByEmail(teamLeaderUsername));
        team.setUserEmail(null);
        teamRepository.create(team);
    }

    public void deleteTeamFormInputHandler(int teamId){
        teamRepository.delete(teamRepository.findById(teamId));
    }

    public void placeHolidayRequestInputHandler(LocalDate firstDay, LocalDate lastDay, String email){
        DayOff dayOff = new DayOff();
        dayOff.setFirstDay(firstDay);
        dayOff.setLastDay(lastDay);
        dayOff.setAccepted(false);
        dayOff.setListOfDays(dayOffService.setListDaysWithoutWeekend(firstDay, lastDay));
        dayOff.setUser(userRepository.findByEmail(email));
        dayOffRepository.create(dayOff);
    }

    public void deleteHolidayRequestFormInputHandler(int requestToDeleteId){
        dayOffRepository.delete(dayOffRepository.findDaysOffByUserId(requestToDeleteId));
    }

}
