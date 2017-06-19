<%@ page import="mainpackage.Doctor" %>
<%
Doctor doc = (Doctor)session.getAttribute("user-info");
%>
	Welcome <%= doc.getUsername() %> !<br>
	
