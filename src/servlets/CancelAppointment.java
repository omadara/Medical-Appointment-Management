package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Scheduler;
import mainpackage.Doctor;
import mainpackage.Patient;
import mainpackage.User;

@WebServlet({ "/patient/CancelAppointment", "/doctor/CancelAppointment" })
public class CancelAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String appointmentDate = request.getParameter("appointmentDate");

		if(ServletUtils.isEmpty(username) || ServletUtils.isEmpty(appointmentDate)){
			ServletUtils.showForm(request, response, "Appointment cancel failed! Empty username or date", "info.jsp");
			return;
		}

		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user-info");
		boolean suc=false;

		if(user==null){
			ServletUtils.showForm(request, response, "Error", "info.jsp");
			return;
		}else if(user instanceof Patient){
			suc = Scheduler.cancelAppointment(username, user.getUsername(), appointmentDate);
			ServletUtils.showForm(request, response, suc?"Success! You cancel your appointment on "+appointmentDate : "Error! please try again", "info.jsp");
			return;
		}else if(user instanceof Doctor){
			suc = Scheduler.cancelAppointment(user.getUsername(), username, appointmentDate);
			ServletUtils.showForm(request, response, suc?"Success! You cancel your appointment on "+appointmentDate : "Error! please try again", "schedule.jsp");
			return;
		}
		ServletUtils.showForm(request, response, "please try again", "index.jsp");
	}

}
