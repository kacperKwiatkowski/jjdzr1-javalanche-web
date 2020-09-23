<%@ page import="java.time.LocalDate" %>
<form action="/forms?placeHolidayRequest" method="post">
    <div class="col-md-6">
        <div class="form-group col">
            <label for="startDay">Choice the first day of your leave:</label>
            <input name="holidayFirstDay" type="date" placeholder="YYYY-MM-DD" class="form-control form-field-width form-group" type="date" id="startDay" name="trip-start"
                   value="<%=LocalDate.now().toString()%>"
                   min="<%=LocalDate.now().toString()%>" max="<%=LocalDate.now().plusDays(366).toString()%>">
        </div>
        <div class="form-group col">
            <label for="endDay">Choice the last day of your leave:</label>
            <input name="holidayLastDay" type="date" class="col form-control form-field-width form-group" type="date" id="endDay" name="trip-end"
                   value="<%=LocalDate.now().toString()%>"
                           min="<%=LocalDate.now().toString()%>" max="<%=LocalDate.now().plusDays(366).toString()%>">
        </div>
        <div class="button-container">
            <button class="button-position btn btn-dark" type="submit" class="btn btn-dark button-position">Submit
            </button>
            <button class="button-position btn btn-dark" type="reset" class="btn btn-dark button-position">Reset
            </button>
        </div>
    </div>
</form>