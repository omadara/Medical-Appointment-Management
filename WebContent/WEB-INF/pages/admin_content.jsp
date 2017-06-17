<%@ page import="mainpackage.Admin" %>
<%
Admin admin = (Admin)session.getAttribute("user-info");
%>
	Welcome <%= admin.getUsername() %> !<br>
	
