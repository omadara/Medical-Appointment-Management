package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Scheduler;
import mainpackage.Availability;

@WebServlet("/NewAppointment")
public class NewAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String spec = request.getParameter("spec");
		if(!isValid(spec)) {
			request.setAttribute("message", "Invalid doctor spec");
			request.getRequestDispatcher("patient_newAppointment.jsp").forward(request, response);
			return;
		}
		
		List<Availability> doctors =  Scheduler.getAvailableDoctors(spec);
		request.setAttribute("doctors", doctors);
		request.getRequestDispatcher("patient_newAppointment.jsp").forward(request, response);
	}

	private boolean isValid(String spec) {
		return "pathologos".equalsIgnoreCase(spec) || "ofthalmiatros".equalsIgnoreCase(spec) || "orthopedikos".equalsIgnoreCase(spec);
	}

}
