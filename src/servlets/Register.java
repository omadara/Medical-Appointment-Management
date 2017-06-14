package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mainpackage.Patient;

@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String amka = request.getParameter("AMKA");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		if (isEmpty(amka) || isEmpty(username) || isEmpty(password) || isEmpty(name) || isEmpty(surname)) {
			showForm(request, response, "One or more fields are empty");
			return;
		}
		
		Patient pat = new Patient();
		pat.setAmka(amka);
		pat.setUsername(username);
		pat.setPassword(password);
		pat.setName(name);
		pat.setSurname(surname);
		if(!database.Accounts.register(pat)){
			showForm(request,response,"AMKA or username already in use");
			return;
		}
		
		//login user
		HttpSession session = request.getSession();
		session.setAttribute("user-info", pat);
		session.setMaxInactiveInterval(60*60);//one hour
		request.getRequestDispatcher("patient.jsp").forward(request, response);
	}
	

	/**
	 *	shows the form with info message
	 */
	public static void showForm(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("message", message);
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	private boolean isEmpty(String input) {
		return input == null || input.trim().isEmpty();
	}

}
