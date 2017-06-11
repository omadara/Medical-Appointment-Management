package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mainpackage.Doctor;
import mainpackage.Patient;
import mainpackage.User;
import servlets.Login;

/**
 * This filters applies to all URL requests and filters out non authenticated users.
 */
@WebFilter("/*" )
public class Authentication implements Filter {
	private final String[] visibles = { "/Login", "/login.jsp",  "/index.jsp", "/register.jsp", "/Register"};

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		HttpSession session = req.getSession(false);
	
		// gia na pernei ola request apo ton server kai oxi apo tin cache tou browser
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);
        
		String reqURL = req.getServletPath().toLowerCase();
		boolean loggedIn = session != null && session.getAttribute("user-info") != null;
		boolean visibleURL = isVisible(reqURL) || reqURL.startsWith("/resources/");
		
		if(!loggedIn && !visibleURL) {
			Login.showForm(req, res, "Please login first.");
			return;
		}
		
		//dei3e to swsto homepage analoga me ton user
		if(loggedIn && reqURL.equals("/index.jsp")) {
			User user = (User)session.getAttribute("user-info");
			if(user instanceof Patient)
				reqURL = "/patient.jsp";
			//...ta ypoloipa homepages pane edw...
			req.getRequestDispatcher(reqURL).forward(request, response);
			return;
		}
		
		chain.doFilter(request, response);
		
	}
	
	private boolean isVisible(String reqURL) {
		for(String s : visibles)
			if(s.toLowerCase().equals(reqURL)) return true;
		return false;
	}
	
	//NOTE: min ta svisete, m petaei error xoris auta
	public void init(FilterConfig conf){ ;}
	public void destroy(){ ;}

}
