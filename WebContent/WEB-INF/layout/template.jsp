<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>${param.title}</title>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/resources/style.css" />
</head>
<body>
	<jsp:include page="/WEB-INF/layout/header.jsp"/>
	<nav>
		<jsp:include page="/WEB-INF/${param.menu}"></jsp:include>
		<!--patient_menu, doctor_menu, admin_menu -->
	</nav>
	<hr>
	<article>
		<h3>${param.title}</h3>
		<jsp:include page="/WEB-INF/${param.content}"/>
	</article>
	<jsp:include page="/WEB-INF/layout/footer.jsp"/>

</body>
</html>
