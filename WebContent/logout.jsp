<%@ page import="mainpackage.Patient" %>
<% Patient pat = (Patient)session.getAttribute("user-info"); 
String name = pat.getName();
session.invalidate(); %>

<jsp:include page="/WEB-INF/layout/template.jsp">
	<jsp:param name="menu" value="layout/not_loggedin_menu.jsp"/>
	<jsp:param name="content" value="pages/logout_content.jsp"/>
	<jsp:param name="title" value="Logout"/>
	<jsp:param name="name" value="<%=name%>"/>
</jsp:include>
