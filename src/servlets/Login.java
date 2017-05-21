package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mainpackage.Patient;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(isEmpty(username) || isEmpty(password)) {
			showForm(request, response, "Empty Username or Password");
			return;
		}
		
		Patient pat = new Patient(username, password);
		if(!pat.login()) {
			showForm(request, response, "Incorrect Credentials");
			return;
		}
		
		//apo8hkeush tou session gia na menei logged in
		HttpSession session = request.getSession();
		session.setAttribute("patient-info", pat);
		
		//TODO show patient page.....
	}

	/**
	 *	shows the form with info message
	 */
	private void showForm(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		response.addHeader("Cache-Control", "no-cache");
		request.setAttribute("message", message);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	private boolean isEmpty(String input) {
		return input == null || input.trim().isEmpty();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
