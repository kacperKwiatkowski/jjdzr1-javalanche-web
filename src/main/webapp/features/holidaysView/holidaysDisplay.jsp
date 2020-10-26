<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container-fluid">
    <div class="container-fluid" style="overflow: auto">
        <br>
        <h3>List of pending holidays:</h3>
        <br>
        <h4>
            <i class="fas fa-search-plus"></i> Search for holidays:
        </h4>
        <input class="form-control" id="myInput" type="text" placeholder="Type here..."><br>
        <table class="table table-striped" cellspacing="0" width="100%">
            <tr>
                <th scope="row">#</th>
                <th scope="row">Employee's e-mail</th>
                <th scope="row">Employee's first name</th>
                <th scope="row">Employee's last name</th>
                <th scope="row">Request's first day</th>
                <th scope="row">Request's last name</th>
                <th scope="row">Action</th>
            </tr>
            <tbody id="usersTable">
            <% int i = 1;%>
            <% for (DayOffDto holidayRequest : pendingHolidayRequests) { %>
                <tr>
                    <td><%=i++%></td>
                    <td><%=holidayRequest.getUser().getEmail()%></td>
                    <td><%=holidayRequest.getUser().getFirstName()%></td>
                    <td><%=holidayRequest.getUser().getLastName()%></td>
                    <td><%=holidayRequest.getFirstDay()%></td>
                    <td><%=holidayRequest.getLastDay()%></td>
                    <td>
                    <div class="row">
                        <div class="btn-toolbar" role="toolbar">
                            <div class="btn-group mr-2" role="group">
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal"
                                        data-target="#editModal<%=holidayRequest.getId()%>">Accept request
                                </button>
                            </div>
                            <div class="btn-group mr-2" role="group">
                                <button type="button" class="btn btn-danger btn-sm" data-toggle="modal"
                                        data-target="#removeModal<%=holidayRequest.getId()%>" id="removeFromTeamButton<%=holidayRequest.getId()%>">Remove request
                                </button>
                            </div>
                        </div>
                    </div>
                    </td>
                </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>

<% for (DayOffDto holidayRequest: pendingHolidayRequests) { %>
<div class="modal fade" id="removeModal<%=holidayRequest.getId()%>" tabindex="-1" role="form">
    <div class="modal-dialog" role="form">
        <div class="modal-content">
            <div class="modal-header">
                <h5>Are you sure you want to decline this request:</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-footer">
                <form method="post" action="/manageholidays?declineholiday">
                    <input type="text" name="holidayId" value="<%=holidayRequest.getId()%>" hidden>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                    <button type="submit" class="btn btn-danger">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editModal<%=holidayRequest.getId()%>" tabindex="-1" role="form">
    <div class="modal-dialog" role="form">
        <div class="modal-content">
            <div class="modal-header">
                <h5>Are you sure you want to accept this request:</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-footer">
                <form method="post" action="/manageholidays?acceptholiday">
                    <input type="text" name="holidayId" value="<%=holidayRequest.getId()%>" hidden>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                    <button type="submit" class="btn btn-danger">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>


<% } %>

