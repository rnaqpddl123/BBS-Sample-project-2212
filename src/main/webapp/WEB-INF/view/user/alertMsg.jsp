<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%
	String msg = (String) request.getAttribute("msg");
	String url = (String) request.getAttribute("url");
%> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>알림 메세지</title>
<link rel="stylesheet" href="http://cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/alertify.min.css"/>
<link rel="stylesheet" href="http://cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/themes/default.min.css"/>
<script src="http://cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/alertify.min.js"></script>"
</head>
<body>
<script>
	/* 반드시 받은 파라메터에 ''를 붙여야함 String이라고 인식못함
	 let 으로 정의하고 넣어도되고 바로 넣어도됨*/
	let msg = "${msg}";
	let url = "${url}";
	alertify.alert(msg, function() {
		alertify.message('OK');
		location.href = url;
	});
</script>
	
</body>
</html>