package servlets;

import java.io.IOException;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mainpackage.Patient;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		if(isEmpty(request.getParameter("AMKA")) ||
				isEmpty(request.getParameter("username")) ||
				isEmpty(request.getParameter("password")) ||
				isEmpty(request.getParameter("name")) ||
				isEmpty(request.getParameter("surname"))
				){
			showForm(request,response,"One or more fields are empty");
		}
		Patient pat = new Patient();
		pat.setAmka(request.getParameter("AMKA"));
		pat.setUsername(request.getParameter("username"));
		pat.setPassword(request.getParameter("password"));
		pat.setName(request.getParameter("name"));
		pat.setSurname(request.getParameter("surname"));
		if(!database.Accounts.register(pat)){
			showForm(request,response,"AMKA or username already in use");
		}
		request.getRequestDispatcher("index.jsp").forward(request, response);
		
	}
	

	/**
	 *	shows the form with info message
	 */
	public static void showForm(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		response.addHeader("Cache-Control", "no-cache");
		request.setAttribute("message", message);
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	private boolean isEmpty(String input) {
		return input == null || input.trim().isEmpty();
	}

}
