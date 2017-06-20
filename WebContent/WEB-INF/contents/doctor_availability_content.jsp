<%@ page import="mainpackage.Doctor, mainpackage.Availability, database.Scheduler, java.util.List, java.text.SimpleDateFormat" %>
<div id="info-message">
	${message}
</div>
<b>When are you available?</b><br>
<i>(format: year-month-day hour:minute)</i><br>
<form action="${pageContext.request.contextPath}/doctor/AddAvailability" method="POST">
	FROM: <input type="text" name="from" value="2017-12-30 18:30"/><br>
	TO: <input type="text" name="to" value="2017-12-30 18:30"/><br>
	<input type="submit" value="Add"/>
</form>


<% List<Availability> avs = Scheduler.getAvailableTimeSpans((Doctor)session.getAttribute("user-info"));
if(avs.isEmpty()){ %>
	<i>Your availability schedule is empty.</i>
<%}else{
	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");%>
	<h3>Your availability schedule</h3>
	<table>
	<tr><th>From</th><th>To</th></tr>
	<%for(Availability av : avs) {%>
	<tr>
		<td><%= f.format(av.getStart()) %></td>
		<td><%= f.format(av.getEnd()) %></td>
	</tr>
 	<%}%>
	</table>
<%}%>
