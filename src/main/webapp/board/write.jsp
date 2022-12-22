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
            <h3><strong>게시글쓰기</strong></h3>
            <hr>
                <div class="row">
			        <div class="col-1"></div>
			        <div class="col-10">
			        	<!-- enctype="multipart/form-data"는 첨부파일 인코딩을 위해서 넣어주어야함 -->
			            <form action="/bbs/board/multiupload" method="post" enctype="multipart/form-data">
			            <!-- <form action="/bbs/board/write" method="post"> -->
			            	<input type="hidden" name="uid" value="${uid}">
			                <table class="table table-borderless">
			                    <tr>
			                        <td><label for="title">제목</label></td>
			                        <td colspan="2"><input class="form-control" type="text" name="title" id="title"></td>
			                    </tr>
			                    <tr>
			                        <td><label for="content">내용</label></td>
			                        <td colspan="2">
			                        	<textarea class="form-control" name="content" id="content" rows="10"></textarea>
			                        </td>
			                    </tr>
			                    <tr>
			                    	<!-- TODO: 나중에 첨부파일수정  -->
			                        <td><label for="files">첨부파일</label></td>
			                        <td><input class="form-control" type="file" name="file1" id="file1" multiple></td>
			                        <td><input class="form-control" type="file" name="file2" id="file2"></td>
			                    </tr>
			                    <tr>
			                        <td colspan="3" style="text-align: center;">
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