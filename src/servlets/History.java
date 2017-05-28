package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Scheduler;
import mainpackage.Appointment;
import mainpackage.Patient;

@WebServlet("/History")
public class History extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null || !session.getAttributeNames().hasMoreElements()) {
			Login.showForm(request, response, "Please login first");
			return;
		}
		
		Patient pat = (Patient)session.getAttribute("patient-info");
		List<Appointment> aps = Scheduler.getAppointmentHistory(pat);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>\r\n" + 
				"<html><head>\r\n" + 
				"<meta charset=\"UTF-8\">\r\n" + 
				"<title>Appointment History</title></head>\r\n" + 
				"<body><h2 align=\"center\">Appointment History</h2>\r\n" + 
				"<TABLE border=\"1\"><tr><th>Doctor's name</th><th>Doctor's surname</th><th>Date</th></tr>");
		for(Appointment ap : aps) {
			out.println("<tr><td>"+ap.getDoctor().getName()+"</td>");
			out.println("<td>"+ap.getDoctor().getSurname()+"</td>");
			out.println("<td>"+ap.getDate()+"</td></tr>");
		}
		out.println("</TABLE></body></html>");
	}

}
