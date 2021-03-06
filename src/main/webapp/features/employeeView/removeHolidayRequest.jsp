<%@ page import="com.infoshareacademy.DTO.DayOffDto" %>
<%@ page import="java.util.List" %>


<% List<DayOffDto> daysOffRequests = (List<DayOffDto>) request.getAttribute("daysOffRequests");%>

<form action="/withdrawHolidayRequestForm" method="post">
    <div class="col-md-6">
        <div class="form-group">
            <label for="exampleFormControlSelect1">Select holiday request to remove:</label>
            <select class="form-control" id="exampleFormControlSelect1" name="selectedHolidayRequestToDelete">
                <%for(DayOffDto dayOffDto : daysOffRequests){%>
                <option value=<%=dayOffDto.getId()%>><%=dayOffDto.getId() + " " + dayOffDto.getFirstDay() + " " + dayOffDto.getLastDay() + " " + dayOffDto.isAccepted()%></option>
                <%}%>
            </select>
        </div>
        <div class="button-container">
            <button class="button-position btn btn-dark" type="submit">Submit</button>
        </div>
    </div>
</form>
