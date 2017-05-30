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

import servlets.Login;

/**
 * This filters applies to all URL requests and filters out non authenticated users.
 */
@WebFilter("/*" )
public class Authentication implements Filter {
	private final String[] visibles = { "/Login", "", "/login.jsp",  "/index.jsp"};

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		//don't filter those
		String reqURL = req.getServletPath().toLowerCase();
		if(isVisible(reqURL) || reqURL.startsWith("/resources/")) {
			chain.doFilter(request, response);
			return;
		}
		
		//check if user is logged in
		HttpSession session = req.getSession(false);
		if(session == null || session.getAttribute("user-info") == null) {
			Login.showForm(req, res, "Please login first.");
		}else{ 
			//hes logged in, let him pass
			chain.doFilter(request, response);			
		}
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
