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
			                        <td><input type="text" name="uid" id="uid" value="${uid}" disabled></td>
			                    </tr>
			                    <tr>
			                        <td><label for="pwd">패스워드</label></td>
			                        <td><input type="password" name="pwd" id="pwd" placeholder="비밀번호를 입력해주세요"></td>
			                    </tr>
			                    <tr>
			                        <td><label for="uname">이름</label></td>
			                        <td><input type="text" name="uname" id="uname" value="${uname}"></td>
			                    </tr>
			                    <tr>
			                        <td><label for="email">이메일</label></td>
			                        <td><input type="text" name="email" id="email" value="${email}"></td>
			                    </tr>
			                    <tr>
			                        <td><label for="regDate">가입날짜</label></td>
			                        <td><input type="text" name="regDate" id="regDate" value="${regDate}" disabled></td>
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