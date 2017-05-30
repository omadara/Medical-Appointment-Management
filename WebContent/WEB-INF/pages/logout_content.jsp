<%@ page import="mainpackage.Patient" %>
<%
Patient pat = (Patient)session.getAttribute("user-info");
%>

		User '<%=pat.getUsername()%>' has been logged out.

		<% session.invalidate();%>

		<br>
		<a href="index.jsp">Click here to continue</a>
