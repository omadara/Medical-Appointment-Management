	<div id="info-message">
		<%
			String s = (String)request.getAttribute("message");
			if(s != null) out.print(s);
		%>
	</div>
	<form action="Register" method="POST">
		<label>AMKA:</label>
		<input type="text" name="AMKA"> <br>
		<label>Username:</label>
		<input type="text" name="username"> <br>
		<label>Password:</label>
		<input type="password" name="password"> <br>
		<label>Name:</label>
		<input type="text" name="name"> <br>
		<label>Surname:</label>
		<input type="text" name="surname"> <br>
		<input type="submit" value="Register"/>
	</form>