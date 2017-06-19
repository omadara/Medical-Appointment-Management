<div id="info-message">
	${message}
</div>
<form action="${pageContext.request.contextPath}/admin/Delete" method="POST">
	Username: <input type="text" name="username"><br>
	<input type="radio" name="acctype" value="doctor"> Doctor | 
	<input type="radio" name="acctype" value="patient"> Patient<br>
	<input type="submit" value="Delete"/>
</form>
