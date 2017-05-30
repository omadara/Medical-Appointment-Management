<%@ page import="mainpackage.Patient" %>
<%
Patient pat = (Patient)session.getAttribute("user-info");
%>
	Welcome patient <%= pat.getUsername() %> !<br>
	
	
	