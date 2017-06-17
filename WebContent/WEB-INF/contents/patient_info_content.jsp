<%@ page import="mainpackage.Patient, mainpackage.Appointment,
database.Scheduler, java.util.List" %>
	<% Patient pat = (Patient)session.getAttribute("user-info"); %>
	<table>
		<tr><td>Username</td><td><%= pat.getUsername() %></td></tr>
		<tr><td>AMKA</td><td><%= pat.getAmka() %></td></tr>
		<tr><td>Name</td><td><%= pat.getName() %></td></tr>
		<tr><td>Surname</td><td><%= pat.getSurname() %></td></tr>
	</table><br>
	
	<h3>Upcoming appointments</h3>
	<% List<Appointment> aps = Scheduler.getSchedule(pat); %>
	<!-- CONTENT HERE -->
	<%if(aps.isEmpty()){ %>
		<i>Your appointment schedule is empty.</i>
	<%}else{%>
		<table>
		<tr><th>Doctor's name</th><th>Doctor's surname</th><th>Date</th></tr>
		<%for(int i=0; i<aps.size();i++){%>
		<tr>
			<td><%= aps.get(i).getDoctor().getName() %></td>
			<td><%= aps.get(i).getDoctor().getSurname() %></td>
			<td><%= aps.get(i).getDate() %></td>
		</tr>
	 	<%}%>
 	<%}%>
	</table>