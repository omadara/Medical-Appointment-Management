<%@ page import="mainpackage.Patient" %>
<%
Patient pat = (Patient)session.getAttribute("user-info");
%>
	<h2>WELCOME PATIENT <%= pat.getUsername() %> </h2>
	blablabla