<%@ page import="mainpackage.Patient, mainpackage.Appointment, java.net.URLEncoder,java.sql.Timestamp,
database.Scheduler, java.util.List, java.text.SimpleDateFormat" %>
	<% Patient pat = (Patient)session.getAttribute("user-info"); 
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");%>
	<table>
		<tr><td>Username</td><td><%= pat.getUsername() %></td></tr>
		<tr><td>AMKA</td><td><%= pat.getAmka() %></td></tr>
		<tr><td>Name</td><td><%= pat.getName() %></td></tr>
		<tr><td>Surname</td><td><%= pat.getSurname() %></td></tr>
	</table><br>
	<div id="info-message">
	${message}
	</div>
	<h3>Upcoming appointments</h3>
	<% List<Appointment> aps = Scheduler.getSchedule(pat); %>
	<!-- CONTENT HERE -->
	<%if(aps.isEmpty()){ %>
		<i>Your appointment schedule is empty.</i>
	<%}else{%>
		<table>
		<tr><th>Doctor's name</th><th>Doctor's surname</th><th>Date</th><th width="40px">X</th></tr>
		<%for(int i=0; i<aps.size();i++){%>
		<tr>
			<td><%= aps.get(i).getDoctor().getName() %></td>
			<td><%= aps.get(i).getDoctor().getSurname() %></td>
			<td><%= f.format(aps.get(i).getDate()) %></td>
			<td><%if( aps.get(i).getDate().after( new Timestamp(System.currentTimeMillis() + 3 * java.util.concurrent.TimeUnit.DAYS.toMillis(1)) )  ){%>
				<a href="${pageContext.request.contextPath}/patient/CancelAppointment?username=<%= URLEncoder.encode(aps.get(i).getDoctor().getUsername(), "UTF-8") %>&appointmentDate=<%=URLEncoder.encode(f.format(aps.get(i).getDate()), "UTF-8") %>">X</a>
				<%} %>
			</td>
		</tr>
	 	<%}%>
 	<%}%>
	</table>