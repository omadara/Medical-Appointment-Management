<div id="info-message">
	${message}
</div>
<form action="${pageContext.request.contextPath}/Delete" method="POST">
	<input type="text" name="username">Username<br>
	<input type="radio" name="acctype" value="doctor"> Doctor | 
	<input type="radio" name="acctype" value="patient"> Patient<br>
	<input type="submit" value="Delete"/>
</form>
