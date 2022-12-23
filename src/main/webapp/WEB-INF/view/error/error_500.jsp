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
				<h1>죄송합니다. 서비스 실행중 오류가 발생하였습니다. </h1>
				<h1>잠시후 다시 시도해 보세요.</h1>
			</div>
			<div class="col-4"></div>
			<!-- ================main========================= -->
		</div>
	</div>

	<%@ include file="../common/bottom.jsp"%>
</body>
</html>