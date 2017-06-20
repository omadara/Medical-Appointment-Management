<%@ page import="mainpackage.Doctor, mainpackage.Appointment, java.sql.Timestamp, java.net.URLEncoder,
database.Scheduler,java.util.List, java.text.SimpleDateFormat" %>
<%
Doctor doc = (Doctor)session.getAttribute("user-info");
List<Appointment> aps = Scheduler.getSchedule(doc);
SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
%>

	<div id="info-message">
	${message}
	</div>
	<%if(aps.isEmpty()){ %>
		<i>Your appointment schedule is empty.</i>
	<%}else{%>
		<table>
		<tr><th>Patient's name</th><th>Patient's surname</th><th>Date</th><th width="40px">X</th></tr>
		<%for(int i=0; i<aps.size();i++){%>
		<tr>
			<td><%= aps.get(i).getPatient().getName() %></td>
			<td><%= aps.get(i).getPatient().getSurname() %></td>
			<td><%= f.format(aps.get(i).getDate()) %></td>
			<td><%if( aps.get(i).getDate().after( new Timestamp(System.currentTimeMillis() + 3 * java.util.concurrent.TimeUnit.DAYS.toMillis(1)) )  ){%>
				<a href="${pageContext.request.contextPath}/doctor/CancelAppointment?username=<%= URLEncoder.encode(aps.get(i).getPatient().getUsername(), "UTF-8") %>&appointmentDate=<%=URLEncoder.encode(f.format(aps.get(i).getDate()), "UTF-8") %>">X</a>
				<%} %>
			</td>
		</tr>
	 	<%}%>
 	<%}%>
	</table>
