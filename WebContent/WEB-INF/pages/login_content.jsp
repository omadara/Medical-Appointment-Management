	<div id="info-message">
		<%
			String s = (String)request.getAttribute("message");
			if(s != null) out.print(s);
		%>
	</div>
	<form action="Login" method="POST">
		<label>Username:</label>
		<input type="text" name="username"> <br>
		<label>Password:</label>
		<input type="password" name="password"> <br>
		<input type="submit" value="Login"/>
	</form>