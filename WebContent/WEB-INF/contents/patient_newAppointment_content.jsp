<%@ page import="mainpackage.Availability, java.util.List, java.net.URLEncoder,
java.sql.Timestamp, java.text.SimpleDateFormat"	pageEncoding="UTF-8"%>
<div id="info-message">
	${message}
</div>
<% List<Availability> doctors = (List<Availability>)request.getAttribute("doctors");
SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
List<String> dateHeaders = (List<String>)request.getAttribute("dateHeaders");
String next = (String)request.getAttribute("next");
String prev = (String)request.getAttribute("prev");
String spec = request.getParameter("spec");
if(doctors==null || dateHeaders==null || dateHeaders.isEmpty()  ) {%>
	<b>Search doctors by specialty:</b><br>
	<form action="${pageContext.request.contextPath}/patient/DoctorSearch" method="GET">
	  <input type="radio" name="spec" value="pathologos" checked> Παθολόγος<br>
	  <input type="radio" name="spec" value="ofthalmiatros"> Οφθαλμίατρος<br>
	  <input type="radio" name="spec" value="orthopedikos"> Ορθοπεδικός<br>
	  <input type="submit" value="Search"/>
	</form>
<%}else{%>
	<a href="${pageContext.request.contextPath}/patient/DoctorSearch?sDate=<%=URLEncoder.encode( prev, "UTF-8")%>&spec=<%=URLEncoder.encode( spec, "UTF-8")%>">&larr;</a>
	<a href="${pageContext.request.contextPath}/patient/DoctorSearch?sDate=<%=URLEncoder.encode( next, "UTF-8")%>&spec=<%=URLEncoder.encode( spec, "UTF-8")%>">&rarr;</a>

	<table>
	<tr>
		<th>Doctor</th>
		<%for(String day : dateHeaders) {%>
			<th><%= day %></th>
		<%}%>
	</tr>
	<%if(doctors.isEmpty()) {%>
		<tr><TD colspan="<%= 1+dateHeaders.size() %>"><i>No doctor available with that specialty.</i></TD></tr>
	<%}else{ %>
		<%for(Availability a : doctors) {%>
		<tr>
			<td><%= a.getDoctor().getName()%> <%=a.getDoctor().getSurname()%></td>
			<%for(List<Timestamp> l : a.getAvail()) {%>
			<td>
				<%if(l.isEmpty()) {%>
					<i>None</i>
				<%}else{%>
					<%for(Timestamp t : l) {%> 
						<br> <a href="${pageContext.request.contextPath}/patient/ScheduleAppointment?docUsername=<%=URLEncoder.encode( a.getDoctor().getUsername(), "UTF-8")%>&appointmentDate=<%=URLEncoder.encode(f.format(t), "UTF-8")%>"> <%=f.format(t).split(" ")[1]%></a> 
					<%}%>
				<%}%>
			</td>
			<%}%>
		</tr>
		<%}%>
	<%}%>
	</table>
<%}%>
