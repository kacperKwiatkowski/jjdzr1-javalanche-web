package com.infoshareacademy.servlets.statistics;

import com.infoshareacademy.DTO.DayOffDto;
import com.infoshareacademy.DTO.UserDto;
import com.infoshareacademy.restapi.Request;
import com.infoshareacademy.service.DayOffService;
import com.infoshareacademy.service.TeamService;
import com.infoshareacademy.service.UserService;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet {

    @Inject
    private DayOffService dayOffService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setRequestDispatcher(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setRequestDispatcher(req, resp);
    }

    private void setRequestDispatcher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        RequestDispatcher view;
        List<DayOffDto> dayOffDtoList = new ArrayList<DayOffDto>(dayOffService.getAll());
        Integer dayOffs = dayOffService.getAll().size();
        Request request = new Request();
        Integer longHolidays = null;
        try{
            longHolidays = request.jsonToList().size();
            if(longHolidays!= null){
            }else{
                longHolidays = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("dayOffs", dayOffDtoList);
        req.setAttribute("longHolidays", longHolidays);
        req.setAttribute("longHolidays1", dayOffs);
        if (req.getSession().getAttribute("username") != null)
            view = getServletContext().getRequestDispatcher("/statisticsView.jsp");
        else {
            view = getServletContext().getRequestDispatcher("/badrequest_404");
        }
        view.forward(req, resp);
    }
}
