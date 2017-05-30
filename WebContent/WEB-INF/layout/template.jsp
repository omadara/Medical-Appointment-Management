<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>${param.title}</title>
<!--
<link rel="stylesheet" type="text/css"
	href="resources/style.css" />
-->
</head>
<body>
	<jsp:include page="/WEB-INF/layout/header.jsp"/>

	<jsp:include page="/WEB-INF/layout/${param.menu}_menu.jsp"></jsp:include>
	<!--patient_menu, doctor_menu, admin_menu -->
	<hr>
	<h3>${param.title}</h3>

	<jsp:include page="/WEB-INF/pages/${param.content}.jsp"/>

	<jsp:include page="/WEB-INF/layout/footer.jsp"/>

</body>
</html>
