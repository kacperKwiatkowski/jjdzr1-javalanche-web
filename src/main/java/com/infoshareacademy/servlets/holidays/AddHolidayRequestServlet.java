package com.infoshareacademy.servlets.holidays;

import com.infoshareacademy.model.Team;
import com.infoshareacademy.model.User;
import com.infoshareacademy.repository.TeamRepository;
import com.infoshareacademy.repository.UserRepository;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addHolidayRequest")
public class AddHolidayRequestServlet extends HttpServlet {

    @Inject
    private TeamRepository teamRepository;

    @Inject
    private UserRepository userRepository;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setRequestDispatcher(req, resp);
    }

    private void setRequestDispatcher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        RequestDispatcher view;
        if (req.getSession().getAttribute("username") != null){

            String name = req.getParameter("addTeamName");
            int assignedTeamLeader = Integer.parseInt(req.getParameter("addTeamsLeader"));
            setNewTeam(name, assignedTeamLeader);

            view = getServletContext().getRequestDispatcher("/teamsView.jsp");
        }
        else {
            view = getServletContext().getRequestDispatcher("/badrequest_404");
        }
        view.forward(req, resp);
    }

    private void setNewTeam(String name, int teamLeaderId) {
        Team team = new Team();
        User user = userRepository.findById(teamLeaderId);
        team.setTeamLeader(user);
        team.setName(name);
        team.setUserEmail(null);
        user.setTeam(team);
        user.setTeamLeader(true);
        userRepository.update(user);
    }

}
