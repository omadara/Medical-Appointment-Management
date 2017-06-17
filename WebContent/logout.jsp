<%@ page import="mainpackage.User" %>
<% User user = (User)session.getAttribute("user-info"); 
String name = user.getName();
session.invalidate(); %>

<jsp:include page="/WEB-INF/layout/template.jsp">
	<jsp:param name="menu" value="layout/not_loggedin_menu.jsp"/>
	<jsp:param name="content" value="contents/logout_content.jsp"/>
	<jsp:param name="title" value="Logout"/>
	<jsp:param name="name" value="<%=name%>"/>
</jsp:include>
