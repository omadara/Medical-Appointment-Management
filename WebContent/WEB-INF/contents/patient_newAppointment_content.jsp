<%@ page import="mainpackage.Availability, java.util.List, java.util.Date, java.sql.Timestamp, java.text.SimpleDateFormat" %>
<div id="info-message">
	${message}
</div>
<% List<Availability> doctors = (List<Availability>)request.getAttribute("doctors");
SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
if(doctors==null) {%>
	<b>Search doctors by specialty:</b><br>
	<form action="${pageContext.request.contextPath}/patient/DoctorSearch" method="POST">
	  <input type="radio" name="spec" value="pathologos" checked> Pathologos<br>
	  <input type="radio" name="spec" value="ofthalmiatros"> Ofthalmiatros<br>
	  <input type="radio" name="spec" value="orthopedikos"> Orthopedikos<br>
	  <input type="submit" value="Search"/>
	</form>
<%}else if(doctors.isEmpty()) {%>
	<i>No doctor available with that specialty.</i>
<%}else{%>
	<table>
	<!-- TODO na allazei tis imerominiess me ta velakia kai na kalei ton /DoctorSearch -->
	<tr><th>Doctor</th><th> &larr; 17/6/2017</th><th>18/6/2017</th><th>19/6/2017</th><th>20/6/2017</th><th>21/6/2017 &rarr;</th></tr>
	<%for(Availability a : doctors) {%>
	<tr>
		<td><%= a.getDoctor().getName()%> <%=a.getDoctor().getSurname()%></td>
		<%for(List<Timestamp> l : a.getAvail()) {%>
			<!-- TODO ta links na kaloun enan neo servlet pou tha kanei insert sta appointments (doc_username, pat_username, t) -->
			<td><%for(Timestamp t : l) {%><br><a href='#'> <%=f.format(t)%> </a><%}%></td>
		<%}%>
	</tr>
	<%}%>
	</table>
<%}%>
