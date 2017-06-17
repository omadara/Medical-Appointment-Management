package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String acctype = request.getParameter("acctype");
		String username = request.getParameter("username");
		if(!isValid(username))
			showForm(request,response,"wrong input");
		boolean valid;
		switch(acctype){
		case "doctor": 
			valid = database.Accounts.deleteDoc(username);
			break;
		case "patient": 
			valid = database.Accounts.deletePatient(username);
			break;
		default :
			showForm(request,response,"Nothing to see here, move along. 'Skyrim hold guards'");
			return;
		}
		if(!valid)
			showForm(request,response,"Something went wrong, Try again.");
		else{
			showForm(request,response,"Succesfully deleted user "+username);
		}
	}
	
	private static void showForm(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("message", message);
		request.getRequestDispatcher("admin/delete.jsp").forward(request, response);
	}
	
	private boolean isValid(String input){
		return input == null || input.trim().isEmpty() || !input.trim().equals("*");
	}

}
