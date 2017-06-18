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

@WebServlet("/DoctorSearch")
public class DoctorSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String spec = request.getParameter("spec");
		if(!ServletUtils.isValidSpec(spec)) {
			request.setAttribute("message", "Invalid doctor spec");
			request.getRequestDispatcher("patient/newAppointment.jsp").forward(request, response);
			return;
		}
		//TODO na parnei tis imerominies apo to POST
		List<Availability> doctors =  Scheduler.getAvailableDoctors(spec.toLowerCase(), "2017-06-17 00:00:00", "2017-06-22 23:59:59");
		request.setAttribute("doctors", doctors);
		request.getRequestDispatcher("patient/newAppointment.jsp").forward(request, response);
	}

}
