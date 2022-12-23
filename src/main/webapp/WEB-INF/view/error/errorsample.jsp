
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8" isErrorPage="true"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- ==================================================================== -->
	<title>Ezen Fulfillment System</title>
	<link href="../css/bootstrap.min.css" rel="stylesheet">
</head>
<jsp:useBean id="now" class="java.util.Date" />
<body>
	<!-- <%@ include file="../common/_top.jspf" %> -->
	
	<div class="container-fluid">
		<div class="row" style="margin-top: 100px">
			<div align=center>
				<h3>Ezen Fulfillment System: error.jsp[web.xml 지정]</h3>
				<hr><br>
				<table>
					<tr width=100% bgcolor="pink"><td>
						관리자에게 문의해 주세요..<br>
						빠른시일내 복구하겠습니다.<br>
					</td></tr>
					<tr><td>
						${now}<p>
						Status code: <c:out value="${requestScope['javax.servlet.error.status_code']}"/><br>
						<c:out value="${requestScope['javax.servlet.error.request_uri']}"/>
						<c:out value="${requestScope['javax.servlet.error.message']}"/>
					</td></tr>
				</table>
			</div>
		</div>
	</div>
	
	<!-- <%@ include file="../common/_bottom.jspf" %> -->
	<!-- ==================================================================== -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
</body>
</html>