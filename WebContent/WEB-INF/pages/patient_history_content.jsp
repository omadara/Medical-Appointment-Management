<%@ page import="mainpackage.Patient, mainpackage.Appointment,
database.Scheduler,java.util.List" %>
<%
Patient pat = (Patient)session.getAttribute("user-info");
List<Appointment> aps = Scheduler.getAppointmentHistory(pat);
%> 

	<!-- CONTENT HERE -->
	<table style="border: 1px solid;">
	<tr><th>Doctor's name</th><th>Doctor's surname</th><th>Date</th></tr>
	<%for(int i=0; i<aps.size();i++){%>
	<tr>
		<td><%= aps.get(i).getDoctor().getName() %></td>
		<td><%= aps.get(i).getDoctor().getSurname() %></td>
		<td><%= aps.get(i).getDate() %></td>
	</tr>
 	<%}%>
	</table>