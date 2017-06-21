package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Scheduler;
import mainpackage.Patient;


@WebServlet("/patient/ScheduleAppointment")
public class ScheduleAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String docUsername = request.getParameter("docUsername");
		String appointmentDate = request.getParameter("appointmentDate");
		HttpSession session = request.getSession();
		Patient pat = (Patient)session.getAttribute("user-info");
		if(ServletUtils.isEmpty(docUsername) || ServletUtils.isEmpty(appointmentDate)) {
			ServletUtils.showForm(request, response, "Empty doctor or date", "newAppointment.jsp");
			return;
		}else if(!Scheduler.isDoctorAvailable(docUsername, appointmentDate)){
			ServletUtils.showForm(request, response, "Doctor "+ docUsername + " isn't available on "+ appointmentDate, "newAppointment.jsp");
			return;
		}
		if(Scheduler.scheduleAppointment(pat, docUsername, appointmentDate)){
			ServletUtils.showForm(request, response, "Success! Your new appointment scheduled on " + appointmentDate, "newAppointment.jsp");
		}else{
			ServletUtils.showForm(request, response, "Error! please try again", "newAppointment.jsp");
		}
	}

}
