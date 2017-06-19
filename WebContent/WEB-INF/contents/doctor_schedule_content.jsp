<%@ page import="mainpackage.Doctor, mainpackage.Appointment,
database.Scheduler,java.util.List, java.text.SimpleDateFormat" %>
<%
Doctor doc = (Doctor)session.getAttribute("user-info");
List<Appointment> aps = Scheduler.getSchedule(doc);
SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
%> 

	<!-- CONTENT HERE -->
	<%if(aps.isEmpty()){ %>
		<i>Your appointment schedule is empty.</i>
	<%}else{%>
		<table>
		<tr><th>Patient's name</th><th>Patient's surname</th><th>Date</th></tr>
		<%for(int i=0; i<aps.size();i++){%>
		<tr>
			<td><%= aps.get(i).getDoctor().getName() %></td>
			<td><%= aps.get(i).getDoctor().getSurname() %></td>
			<td><%= f.format(aps.get(i).getDate()) %></td>
		</tr>
	 	<%}%>
 	<%}%>
	</table>
