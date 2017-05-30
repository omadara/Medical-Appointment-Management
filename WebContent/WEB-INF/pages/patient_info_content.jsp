<%@ page import="mainpackage.Patient" %>
<%
Patient pat = (Patient)session.getAttribute("user-info");
%>
	<table border="1">
		<tr><td>Username</td><td><%= pat.getUsername() %></td></tr>
		<tr><td>AMKA</td><td><%= pat.getAmka() %></td></tr>
		<tr><td>Name</td><td><%= pat.getName() %></td></tr>
		<tr><td>Surname</td><td><%= pat.getSurname() %></td></tr>
	</table>