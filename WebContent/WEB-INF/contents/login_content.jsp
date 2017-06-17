	<div id="info-message">
		${message}
	</div>
	<form action="${pageContext.request.contextPath}/Login" method="POST">
		<label>Username:</label>
		<input type="text" name="username"> <br>
		<label>Password:</label>
		<input type="password" name="password"> <br>
		<input type="radio" name="type" value="patient" checked> Patient |
	  	<input type="radio" name="type" value="doctor"> Doctor |
	  	<input type="radio" name="type" value="admin"> Administrator<br>
		<input type="submit" value="Login"/>
	</form>