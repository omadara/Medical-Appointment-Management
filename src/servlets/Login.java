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
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		if(ServletUtils.isEmpty(username) || ServletUtils.isEmpty(password)) {
			ServletUtils.showForm(request, response, "Empty Username or Password", "login.jsp");
			return;
		}else if(!"patient".equals(type) && !"doctor".equals(type) && !"admin".equals(type)) {
			ServletUtils.showForm(request, response, "Invalid account type", "login.jsp");
			return;
		}
		
		User user = null;
		switch(type) {
		case "patient": user = Accounts.getPatient(username, ServletUtils.encrypt(password));break;
		case "doctor": user = Accounts.getDoctor(username, ServletUtils.encrypt(password));break;
		case "admin": user = Accounts.getAdmin(username, ServletUtils.encrypt(password));break;
		}
		if(user == null) {
			ServletUtils.showForm(request, response, "Incorrect Credentials", "login.jsp");
			return;
		}
		
		//apo8hkeush tou session gia na menei logged in
		HttpSession session = request.getSession();
		session.setAttribute("user-info", user);
		session.setMaxInactiveInterval(60*60);//one hour
		//phgene ton sto homepage tou analoga me to typo user
		request.getRequestDispatcher(type+"/index.jsp").forward(request, response);
	}

}
