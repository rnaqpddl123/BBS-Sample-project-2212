<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>회원 수정</title>
 	<style>
		td { text-align: center; padding: 3px; }
 	</style>
</head>
<body style="margin:40px">
	<h1>회원정보수정</h1>
	<hr>
	<form action="/bbs/user/update" method="post">
		<input type="hidden" name="uid" value="${uid}">
		<table>
			<tr>
				<td>사용자</td>
				<td><input type="text" name="uid" value="${uid}" disabled></td>
			</tr>
			<tr>
				<td>비밀번호 확인</td>
				<td><input type="password" name="pwd" placeholder="비밀번호를 입력해주세요"></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" name="uname" value="${uname}"></td>
			</tr>
			<tr>
				<td>email</td>
				<td><input type="email" name="email" value="${email}"></td>
			</tr>
			<tr>
				<td>가입날짜</td>
				<td><input type="text" name="regDate" value="${regDate}" disabled></td>
			</tr>
			<tr>
				 <td colspan="2"><input type="submit" value="수정">
				 <input type="reset" value="취소"></td>
			</tr>
		</table>
	</form>
</body>
</html>