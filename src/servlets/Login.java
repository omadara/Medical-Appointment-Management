package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Accounts;
import mainpackage.User;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		if(isEmpty(username) || isEmpty(password)) {
			showForm(request, response, "Empty Username or Password");
			return;
		}else if(!"patient".equals(type) && !"doctor".equals(type) && !"admin".equals(type)) {
			showForm(request, response, "Invalid account type");
			return;
		}
		
		User user = null;
		switch(type) {
		case "patient": user = Accounts.getPatient(username, password);break;
		case "doctor": user = Accounts.getDoctor(username, password);break;
		case "admin": user = Accounts.getAdmin(username, password);break;
		}
		if(user == null) {
			showForm(request, response, "Incorrect Credentials");
			return;
		}
		
		//apo8hkeush tou session gia na menei logged in
		HttpSession session = request.getSession();
		session.setAttribute("user-info", user);
		session.setMaxInactiveInterval(60*60);//one hour
		//phgene ton sto homepage tou analoga me to typo user
		request.getRequestDispatcher(type+"/index.jsp").forward(request, response);
	}

	/**
	 *	shows the form with info message
	 */
	public static void showForm(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("message", message);
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	private boolean isEmpty(String input) {
		return input == null || input.trim().isEmpty();
	}

}
