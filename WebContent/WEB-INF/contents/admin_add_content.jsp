	<div id="info-message">
		${message}
	</div>
	<form action="${pageContext.request.contextPath}/admin/RegisterDoctor" method="POST">
		<input type="radio" name="spec" value="pathologos" checked> Pathologos<br>
	  	<input type="radio" name="spec" value="ofthalmiatros"> Ofthalmiatros<br>
	  	<input type="radio" name="spec" value="orthopedikos"> Orthopedikos<br>
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
