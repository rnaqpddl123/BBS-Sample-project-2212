<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% pageContext.setAttribute("newlinez", "\n"); %>
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
            <h3><strong>게시글 수정</strong></h3>
            <hr>
                <div class="row">
			        <div class="col-1"></div>
			        <div class="col-10">
			            <form action="/bbs/board/update" method="post">
			            	<input type="hidden" name="bid" value="${board.bid}">
			            	<input type="hidden" name="uid" value="${board.uid}">
			                <table class="table table-borderless">
			                    <tr>
			                        <td><label for="title">제목</label></td>
			                        <td><input class="form-control" type="text" name="title" id="title" value="${board.title}"></td>
			                    </tr>
			                    <tr>
			                        <td><label for="content">내용</label></td>
			                        <td>
			                        	<textarea class="form-control" name="content" id="content" rows="10">${fn:replace(board.content, newline, '<br>')}
			                        	</textarea>
			                        </td>
			                    </tr>
			                    <tr>
			                    	<!-- TODO: 나중에 첨부파일수정  -->
			                        <td><label for="files">첨부파일</label></td>
			                        <td><input class="form-control" type="text" name="files" id="files" value="${board.files}"></td>
			                    </tr>
			                    <tr>
			                        <td colspan="2" style="text-align: center;">
			                            <input class="btn btn-primary" type="submit" value="수정">
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