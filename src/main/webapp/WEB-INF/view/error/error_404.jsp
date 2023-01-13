<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="../common/heading.jsp"%>
<style>
th, td {
	text-align: center;
}
</style>
</head>

<body>
	<%@ include file="../common/top.jsp"%>

	<div class="container" style="margin-top: 80px;">

		<!-- 나머지는 공통이니까 main부분만 고치면됨 -->
		<!-- =============================main=========================== -->
		<div class="row">
			<div class="col-4"></div>
			<div class="col-4">
				<h3>
					<strong>에러 페이지</strong>
				</h3>
				<hr>
				<h1>404 error</h1>
				<h1>요청한 페이지는 존재하지 않습니다.</h1>
			</div>
			<div class="col-4"></div>
			<!-- ================main========================= -->
		</div>
	</div>

	<%@ include file="../common/bottom.jsp"%>
</body>
</html>