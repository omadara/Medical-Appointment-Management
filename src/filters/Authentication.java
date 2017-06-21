package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mainpackage.Admin;
import mainpackage.Doctor;
import mainpackage.Patient;
import mainpackage.User;
import servlets.ServletUtils;

/**
 * This filters applies to all URL requests and filters out non authenticated users.
 */
@WebFilter("/*" )
public class Authentication implements Filter {
	private final String[] visibles = { "/Login", "/login.jsp",  "/index.jsp", "/register.jsp", "/Register", "/about.jsp"};
	private enum UserType{ADMIN,DOCTOR,PATIENT};

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		HttpSession session = req.getSession(false);

		res.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		// gia na pernei ola request apo ton server kai oxi apo tin cache tou browser
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		res.setHeader("Pragma", "no-cache");
		res.setDateHeader("Expires", 0);
		
		String reqURL = req.getServletPath().toLowerCase();
		User user = session!=null ? (User)session.getAttribute("user-info") : null;
		boolean loggedIn = user != null;
		boolean visibleURL = isVisible(reqURL) || reqURL.startsWith("/resources/");
		UserType type = null;
		if(user!=null && user instanceof Patient) type = UserType.PATIENT;
		else if(user!=null && user instanceof Doctor) type = UserType.DOCTOR;
		else if(user!=null && user instanceof Admin) type = UserType.ADMIN;
		
		if(!loggedIn && visibleURL) {
			chain.doFilter(request, response);
			return;
		}
		if(!loggedIn && !visibleURL) {
			req.setAttribute("message", "Please login first.");
			//xreiazomaste to dispatcher apo to context gia "apolytes" diadromes mesa sto servlet
			//enw to dispatcher apo to request dinei mono se "topikes" diadromes apo to teleutaio "fakelo" pou klh8hke
			req.getServletContext().getRequestDispatcher("/login.jsp").forward(req, res);
			return;
		}
		
		//dei3e to swsto homepage analoga me ton user
		if(reqURL.equals("/index.jsp")) {
			if(type == UserType.PATIENT) reqURL = "/patient/index.jsp";
			else if(type == UserType.DOCTOR) reqURL = "/doctor/index.jsp";
			else if(type == UserType.ADMIN) reqURL = "/admin/index.jsp";
			req.getRequestDispatcher(reqURL).forward(request, response);
			return;
		}
		
		//apetrepse tous patient na vlepoun selides twn doctor/admin ktlp
		if(!isAuthorized(type, reqURL)) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		chain.doFilter(request, response);
	}
	
	private boolean isAuthorized(UserType type, String reqURL) {
		return (type == UserType.PATIENT && !reqURL.startsWith("/admin/") && !reqURL.startsWith("/doctor/"))
				|| (type == UserType.DOCTOR && !reqURL.startsWith("/admin/") && !reqURL.startsWith("/patient/"))
				|| (type == UserType.ADMIN && !reqURL.startsWith("/patient/") && !reqURL.startsWith("/doctor/"));
	}

	private boolean isVisible(String reqURL) {
		for(String s : visibles)
			if(s.toLowerCase().equals(reqURL)) return true;
		return false;
	}
	
	public void init(FilterConfig conf){ ;}
	public void destroy(){ ;}

}
