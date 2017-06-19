package servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ServletUtils {
	
	public static String encrypt(String input) {
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] salt = {13, 126, 10, 123, 124, 99, 12, 1, 34, 52};
			sha.update(input.getBytes("UTF-8"));
			sha.update(salt);
			byte[] hash = sha.digest();
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
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
