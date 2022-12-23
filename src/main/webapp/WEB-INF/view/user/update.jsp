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
            <h3><strong>회원정보수정</strong></h3>
            <hr>
                <div class="row">
			        <div class="col-3"></div>
			        <div class="col-6">
			            <form action="/bbs/user/update" method="post">
			            	<input type="hidden" name="uid" value="${uid}">
			                <table class="table table-borderless">
			                    <tr>
			                        <td><label for="uid">사용자 ID</label></td>
			                        <td><input type="text" name="uid" value="${uid}" disabled></td>
			                    </tr>
			                    <!-- TODO: 세션pwd가 있으면 비밀번호도 변경할수 있게 비밀번호 변경도 같이되게 만들기 -->
			                    <!-- 현재비밀번호, 바꿀비밀번호, 바꿀 비밀번호확인창 만들기 세션보다는 버튼식이 나을듯 -->
				                <tr>
				                    <td><label for="pwd">현재 비밀번호</label></td>
				                    <td><input type="password" name="pwd" placeholder="현재 비밀번호를 입력해주세요"></td>
				                </tr>
				                <tr>
				                    <td><label for="pwd">비밀번호 변경</label></td>
				                    <td><input type="password" name="pwd2" placeholder="바꾸실 비밀번호를 입력해주세요"></td>
				                </tr>
				                <tr>
				                    <td><label for="pwd">비밀번호 변경 확인</label></td>
				                    <td><input type="password" name="pwd3" placeholder="바꾸실 비밀번호를 한번더 입력해주세요"></td>
				                </tr>
			                    <tr>
			                        <td><label for="uname">이름</label></td>
			                        <td><input type="text" name="uname" value="${uname}"></td>
			                    </tr>
			                    <tr>
			                        <td><label for="email">이메일</label></td>
			                        <td><input type="text" name="email" value="${email}"></td>
			                    </tr>
			                    <tr>
			                        <td><label for="regDate">가입날짜</label></td>
			                        <td><input type="text" name="regDate" value="${regDate}" disabled></td>
			                    </tr>
			                    <tr>
			                        <td colspan="2" style="text-align: center;">
			                            <input class="btn btn-primary" type="submit" value="제출">
			                            <input class="btn btn-secondary" type="reset" value="취소">
			                        </td>
			                    </tr>
			                </table>
			            </form>
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