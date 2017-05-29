package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mainpackage.Patient;

@WebServlet("/Info")
public class Info extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		Patient pat = (Patient)session.getAttribute("user-info");
		response.setContentType("text/html");
		response.getWriter().println("<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"<meta charset=\"UTF-8\">\r\n" + 
				"<title>Info Page</title>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"<h3>Your Info</h3>\r\n" + 
				"<TABLE border=\"1\">\r\n" + 
				"	<TR><TD>Username</TD><TD>"+pat.getUsername()+"</TD></TR>\r\n" + 
				"	<TR><TD>AMKA</TD><TD>"+pat.getAmka()+"</TD></TR>\r\n" + 
				"	<TR><TD>Name</TD><TD>"+pat.getName()+"</TD></TR>\r\n" + 
				"	<TR><TD>Surname</TD><TD>"+pat.getSurname()+"</TD></TR>\r\n" + 
				"</TABLE>\r\n" + 
				"</body>\r\n" + 
				"</html>");
	}

}
