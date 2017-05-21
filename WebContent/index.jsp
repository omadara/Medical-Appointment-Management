<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
</head>
<body>
<h2 align="center">Login Page</h2>
<div id="info-message">
	<%
		String s = (String)request.getAttribute("message");
		if(s != null) out.print(s);
	%>
</div>
<form action="Login" method="POST">
	<label>First name:</label>
	<input type="text" name="username"> <br>
	<label>Password:</label>
	<input type="password" name="password"> <br>
	<input type="submit" value="Login"/>
</form>
</body>
</html>