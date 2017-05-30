package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Accounts;
import mainpackage.Patient;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(isEmpty(username) || isEmpty(password)) {
			showForm(request, response, "Empty Username or Password");
			return;
		}
		
		//NOTE: prepei na anagnorizei an ekane login ws patien, doctor h admin kai oxi mono patient
		Patient pat = Accounts.getPatient(username, password);
		if(pat == null) {
			showForm(request, response, "Incorrect Credentials");
			return;
		}
		
		//apo8hkeush tou session gia na menei logged in
		HttpSession session = request.getSession();
		session.setAttribute("user-info", pat);
		session.setMaxInactiveInterval(60*60);//one hour
		request.getRequestDispatcher("patient.jsp").forward(request, response);
	}

	/**
	 *	shows the form with info message
	 */
	public static void showForm(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		response.addHeader("Cache-Control", "no-cache");
		request.setAttribute("message", message);
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	private boolean isEmpty(String input) {
		return input == null || input.trim().isEmpty();
	}

}
