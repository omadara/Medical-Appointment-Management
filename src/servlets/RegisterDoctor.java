package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mainpackage.Doctor;

@WebServlet("/admin/RegisterDoctor")
public class RegisterDoctor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String spec = request.getParameter("spec");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		if (ServletUtils.isEmpty(username) || ServletUtils.isEmpty(password) || ServletUtils.isEmpty(name) || ServletUtils.isEmpty(surname)) {
			ServletUtils.showForm(request, response, "One or more fields are empty", "add.jsp");
			return;
		}else if(!ServletUtils.isValidSpec(spec)) {
			ServletUtils.showForm(request, response , "Invalid doctor spec", "add.jsp");
			return;
		}
		
		Doctor doc = new Doctor();
		doc.setSpeciality(spec);
		doc.setUsername(username);
		doc.setPassword(ServletUtils.encrypt(password));
		doc.setName(name);
		doc.setSurname(surname);
		if(!database.Accounts.register(doc)){
			ServletUtils.showForm(request,response,"Username already in use", "add.jsp");
			return;
		}else{
			ServletUtils.showForm(request,response,"Successful registration of doctor: "+doc.getUsername(), "add.jsp");
			return;
		}
	}
	
}
