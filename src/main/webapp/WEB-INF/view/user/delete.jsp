<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file="../common/heading.jsp" %>
	
    <style>
        th, td { text-align: center; }
    </style>
</head>

<body>
   	<%@ include file="../common/top.jsp" %>

    <div class="container" style="margin-top: 80px;">
        <div class="row">
            <%@ include file="../common/aside.jsp" %>
            <!-- 나머지는 공통이니까 main부분만 고치면됨 -->
            <!-- =============================main=========================== -->
            <div class="col-sm-9">
            <h3><strong>회원 탈퇴</strong></h3>
            <hr>
                <div class="row">
			        <div class="col-3"></div>
			        <div class="col-6">
			            <div class="card-body">
			            	<strong class="card-title">삭제하시겠습니까?</strong>
			            	<p class="card-text text-center">
			            		<br>
			            		<button class="btn btn-primary" onclick="location.href='/bbs/user/deleteConfirm?uid=${param.uid}'">삭제</button>
			            		<button class="btn btn-primary" onclick="location.href='/bbs/user/list?page=${currentUserPage}'">취소</button>
			            	</p>
			            </div>
			        </div>
			        <div class="col-3"></div>
			    </div>
            </div>
            <!-- ================main========================= -->
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
</body>
</html>