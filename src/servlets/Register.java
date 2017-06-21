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
		String amka = request.getParameter("AMKA");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		if (ServletUtils.isEmpty(amka) || ServletUtils.isEmpty(username) || ServletUtils.isEmpty(password) || ServletUtils.isEmpty(name) || ServletUtils.isEmpty(surname)) {
			ServletUtils.showForm(request, response, "One or more fields are empty", "register.jsp");
			return;
		}
		
		Patient pat = new Patient();
		pat.setAmka(amka);
		pat.setUsername(username);
		pat.setPassword(ServletUtils.encrypt(password));
		pat.setName(name);
		pat.setSurname(surname);
		if(!database.Accounts.register(pat)){
			ServletUtils.showForm(request,response,"AMKA or username already in use", "register.jsp");
			return;
		}
		
		//login user
		HttpSession session = request.getSession();
		session.setAttribute("user-info", pat);
		session.setMaxInactiveInterval(60*60);//one hour
		request.getRequestDispatcher("patient/index.jsp").forward(request, response);
	}

}
