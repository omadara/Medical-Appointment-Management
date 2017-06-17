package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mainpackage.Doctor;

@WebServlet("/RegisterDoctor")
public class RegisterDoctor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String spec = request.getParameter("spec");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		if (isEmpty(username) || isEmpty(password) || isEmpty(name) || isEmpty(surname)) {
			showForm(request, response, "One or more fields are empty");
			return;
		}else if(!isValid(spec)) {
			showForm(request, response , "Invalid doctor spec");
			return;
		}
		
		Doctor doc = new Doctor();
		doc.setSpeciality(spec);
		doc.setUsername(username);
		doc.setPassword(password);
		doc.setName(name);
		doc.setSurname(surname);
		if(!database.Accounts.register(doc)){
			showForm(request,response,"Username already in use");
			return;
		}else{
			showForm(request,response,"Successful registration of doctor: "+doc.getUsername());
			return;
		}
	}
	
	/**
	 *	shows the form with info message
	 */
	public static void showForm(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("message", message);
		request.getRequestDispatcher("admin/add.jsp").forward(request, response);
	}

	private boolean isEmpty(String input) {
		return input == null || input.trim().isEmpty();
	}
	
	private boolean isValid(String spec) {
		return "pathologos".equalsIgnoreCase(spec) || "ofthalmiatros".equalsIgnoreCase(spec) || "orthopedikos".equalsIgnoreCase(spec);
	}
}
