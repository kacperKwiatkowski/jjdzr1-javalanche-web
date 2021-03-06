package com.infoshareacademy.servlets.holidays;

import com.infoshareacademy.model.DayOff;
import com.infoshareacademy.model.User;
import com.infoshareacademy.repository.DayOffRepository;
import com.infoshareacademy.repository.UserRepository;
import com.infoshareacademy.service.DayOffService;
import com.infoshareacademy.service.HolidayService;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/addHolidayRequest")
public class AddHolidayRequestServlet extends HttpServlet {

    @Inject
    private DayOffRepository dayOffRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private DayOffService dayOffService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setRequestDispatcher(req, resp);
    }

    private void setRequestDispatcher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        RequestDispatcher view;
        if (req.getSession().getAttribute("username") != null) {

            performRequestWithValidation(req);

            view = getServletContext().getRequestDispatcher("/main");

        } else {
            view = getServletContext().getRequestDispatcher("/badrequest_404");
        }
        view.forward(req, resp);
    }

    private void performRequestWithValidation(HttpServletRequest req) {
        String decision = "";
        String task = "";
        String message = "";
        boolean status = false;

        try {
            LocalDate startDay = LocalDate.parse(req.getParameter("StartDate"));
            LocalDate endDay = LocalDate.parse(req.getParameter("EndDate"));
            setNewHolidayRequest(startDay, endDay, req);
            message = "placed successfully";
            status = true;
        } catch (Exception e){
            message = "placed unsuccessfully";
        }

        req.getSession().setAttribute("task", "Holiday request");
        req.getSession().setAttribute("message", message);
        req.getSession().setAttribute("success", status);
    }

    public void setNewHolidayRequest(LocalDate startDay, LocalDate endDay, HttpServletRequest req) {

        User user = userRepository.findByEmail(req.getSession().getAttribute("username").toString());
        DayOff dayOff = prepareDayOff(startDay, endDay, user);
        List<DayOff> overlappingRequests = dayOffRepository.getOverlappingHolidays(dayOff);

        if (overlappingRequests.size() > 0) {
            int numberOfDaysOffRemainingToAdd = appendUsersHolidayRequests(user, dayOff, overlappingRequests);
            user.setDaysOffLeft((int) (user.getDaysOffLeft() - dayOff.getListOfDays().size() + numberOfDaysOffRemainingToAdd));
            userRepository.update(user);
            clearOverlappingHolidayRequests(overlappingRequests);
        } else {
            user.setDaysOffLeft((int) (user.getDaysOffLeft() - dayOff.getListOfDays().size()));
            userRepository.update(user);
            dayOffRepository.create(dayOff);
        }

    }

    private int appendUsersHolidayRequests(User user, DayOff dayOff, List<DayOff> overlappingRequests) {
        Set<LocalDate> listOfDaysOff = new HashSet<>();

        overlappingRequests
                .stream().map(entry -> listOfDaysOff
                        .addAll(entry
                                .getListOfDays())).collect(Collectors.toSet()).size();

        listOfDaysOff
                .stream()
                .filter(date -> dayOff.getFirstDay().isAfter(date) | dayOff.getLastDay().isBefore(date));

        Set<DayOff> setOfDaysOffRequestsToAppend = user.getDaysOff();
        setOfDaysOffRequestsToAppend.removeAll(overlappingRequests.stream().collect(Collectors.toSet()));
        setOfDaysOffRequestsToAppend.add(dayOff);
        user.setDaysOff(setOfDaysOffRequestsToAppend);
        return listOfDaysOff.size();
    }

    private void clearOverlappingHolidayRequests(List<DayOff> overlappingRequests) {
        for (DayOff entry : overlappingRequests) {
            entry.setListOfDays(null);
            entry.setUser(null);
            dayOffRepository.update(entry);
            dayOffRepository.delete(entry);
        }
    }

    private DayOff prepareDayOff(LocalDate startDay, LocalDate endDay, User user) {
        DayOff dayOff = new DayOff();
        dayOff.setUser(user);
        dayOff.setAccepted(user.getLevelOfAccess() == 3);
        dayOff.setFirstDay(startDay);
        dayOff.setLastDay(endDay);
        dayOff.setListOfDays(dayOffService.setListDaysWithoutWeekend(startDay, endDay));
        return dayOff;
    }

}
