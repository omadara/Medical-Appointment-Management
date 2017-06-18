package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ServletUtils {
	
	public static void showForm(HttpServletRequest request, HttpServletResponse response, String message, String form) throws ServletException, IOException {
		request.setAttribute("message", message);
		request.getRequestDispatcher(form).forward(request, response);
	}
	
	public static boolean isEmpty(String input) {
		return input == null || input.trim().isEmpty();
	}
	
	public static boolean isValidAccType(String acctype) {
		return "doctor".equals(acctype) || "patient".equals(acctype);
	}
	
	public static boolean isValidSpec(String spec) {
		return "pathologos".equalsIgnoreCase(spec) || "ofthalmiatros".equalsIgnoreCase(spec) || "orthopedikos".equalsIgnoreCase(spec);
	}
}
