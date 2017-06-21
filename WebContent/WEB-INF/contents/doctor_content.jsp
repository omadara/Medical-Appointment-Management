<%@ page import="mainpackage.Doctor" %>
<%
Doctor doc = (Doctor)session.getAttribute("user-info");
%>
	Welcome <%= doc.getUsername() %> !<br>
	<br><h3>Your Infos:</h3>
	<table>
		<tr><td>Username</td><td><%= doc.getUsername() %></td></tr>
		<tr><td>Specialty</td><td><%= doc.getSpeciality() %></td></tr>
		<tr><td>Name</td><td><%= doc.getName() %></td></tr>
		<tr><td>Surname</td><td><%= doc.getSurname() %></td></tr>
	</table><br>