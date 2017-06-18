package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String acctype = request.getParameter("acctype");
		String username = request.getParameter("username");
		if(ServletUtils.isEmpty(username)) {
			ServletUtils.showForm(request, response, "Empty username", "admin/delete.jsp");
			return;
		}else if(!ServletUtils.isValidAccType(acctype)) {
			ServletUtils.showForm(request, response, "Invalid account type", "admin/delete.jsp");
			return;
		}
		boolean success;
		switch(acctype){
		case "doctor": success = database.Accounts.deleteDoc(username); break;
		case "patient": success = database.Accounts.deletePatient(username); break;
		default :
			ServletUtils.showForm(request,response,"Invalid account type", "admin/delete.jsp");
			return;
		}
		if(success) ServletUtils.showForm(request,response,"Succesfully deleted user "+username, "admin/delete.jsp");
		else ServletUtils.showForm(request,response,"Couldnt not find user: "+username, "admin/delete.jsp");
	}

}
