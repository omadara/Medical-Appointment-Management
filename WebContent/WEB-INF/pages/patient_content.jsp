<%@ page import="mainpackage.Patient" %>
<%
Patient pat = (Patient)session.getAttribute("user-info");
%>
	Welcome <%= pat.getUsername() %> !<br>
	
	
	