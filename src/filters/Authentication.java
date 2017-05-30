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

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;

		//user is trying to login(aka access login servlet)
		System.out.println(req.getServletPath());
		if (req.getServletPath().toLowerCase().equals("/login")) {
			chain.doFilter(request, response);
			return;
		}
		///dont filter index and resources/*
		if(req.getServletPath().toLowerCase().equals("")
				||req.getServletPath().toLowerCase().equals("/index.jsp")
				||req.getServletPath().toLowerCase().matches("^/resources/.+")){
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
	//NOTE: min ta svisete, m petaei error xoris auta
	public void init(FilterConfig conf){ ;}
	public void destroy(){ ;}

}
