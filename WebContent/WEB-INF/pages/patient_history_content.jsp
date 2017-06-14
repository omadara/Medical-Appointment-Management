<%@ page import="mainpackage.Patient, mainpackage.Appointment,
database.Scheduler,java.util.List" %>
<%
Patient pat = (Patient)session.getAttribute("user-info");
List<Appointment> aps = Scheduler.getAppointmentHistory(pat);
%> 

	<!-- CONTENT HERE -->
	<%if(aps.isEmpty()){ %>
		<i>Your appointment history is empty.</i>
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