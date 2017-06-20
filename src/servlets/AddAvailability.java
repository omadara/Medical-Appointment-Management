package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Scheduler;
import mainpackage.Doctor;

@WebServlet("/doctor/AddAvailability")
public class AddAvailability extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		if(ServletUtils.isEmpty(from) || ServletUtils.isEmpty(to)) {
			ServletUtils.showForm(request, response, "Empty date(s)", "availability.jsp");
			return;
		}
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date start, end;
		try {
			start = f.parse(from);
			end = f.parse(to);
		} catch (ParseException e) {
			ServletUtils.showForm(request, response, "Invalid Syntax", "availability.jsp");
			return;
		}
		if(end.compareTo(start) <= 0) {
			ServletUtils.showForm(request, response, "Date 'from' must be earlier than date 'to'", "availability.jsp");
			return;
		}
		Doctor doc = (Doctor)request.getSession().getAttribute("user-info");
		if(!Scheduler.setAvailable(doc, from, to)) {
			ServletUtils.showForm(request, response, "Invalid dates. Either an availability or an appointment is between", "availability.jsp");
		}else{
			ServletUtils.showForm(request, response, "Successful", "availability.jsp");
		}
	}

}
