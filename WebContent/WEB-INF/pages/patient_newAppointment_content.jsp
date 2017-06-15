<%@ page import="mainpackage.Availability, java.util.List" %>
<div id="info-message">
	${message}
</div>
<% List<Availability> doctors = (List<Availability>)request.getAttribute("doctors");
if(doctors==null) {%>
	<b>Search doctors by specialty:</b><br>
	<form action="DoctorSearch" method="POST">
	  <input type="radio" name="spec" value="pathologos" checked> Pathologos<br>
	  <input type="radio" name="spec" value="ofthalmiatros"> Ofthalmiatros<br>
	  <input type="radio" name="spec" value="orthopedikos"> Orthopedikos<br>
	  <input type="submit" value="Search"/>
	</form>
<%}else if(doctors.isEmpty()) {%>
	<i>No doctor available with that specialty.</i>
<%}else{%>
	<table>
	<tr><th>Doctor's name</th><th>Doctor's surname</th><th>At</th><th>Until</th></tr>
	<%for(Availability a : doctors) {%>
	<tr>
		<td><%= a.getDoctor().getName() %></td>
		<td><%= a.getDoctor().getSurname() %></td>
		<td><%= a.getStart() %></td>
		<td><%= a.getEnd() %></td>
	</tr>
	<%}%>
	</table>
<%}%>
