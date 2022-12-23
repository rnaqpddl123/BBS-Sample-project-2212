<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file="../common/heading.jsp" %>
    <style>
        th, td { text-align: center; }
    </style>
    <script src="https://cdn.ckeditor.com/4.18.0/standard/ckeditor.js"></script>
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
				    <!-- enctype="multipart/form-data"는 첨부파일 인코딩을 위해서 넣어주어야함 -->
				    <form action="/bbs/board/fileUpload?dest=write" method="post" enctype="multipart/form-data">
				    	<input type="hidden" name="uid" value="${uid}">
				        <table class="table table-borderless">
				            <tr class="d-flex">
				                <td class="col-1"><label for="title">제목</label></td>
				                <td class="col-11">
				                	<input class="form-control" type="text" name="title" id="title">
				                </td>
				            </tr>
				            <tr class="d-flex">
				                <td class="col-1"><label for="content">내용</label></td>
				                <td class="col-11">
				                	<textarea class="form-control" name="content" id="content" rows="10"></textarea>
				                </td>
				            </tr>
				            <tr class="d-flex">
				            	<!-- TODO: 나중에 첨부파일수정  -->
				                <td class="col-1"><label for="files">첨부파일</label></td>
				                <td class="col-11"><input class="form-control" type="file" name="files" id="files" multiple></td>
				            </tr>
				            <tr class="d-flex">
				                <td colspan="3" style="text-align: center;">
				                    <input class="btn btn-primary" type="submit" value="제출">
				                    <input class="btn btn-secondary" type="reset" value="취소">
				                </td>
				            </tr>
				        </table>
				    </form>
			    </div>
            </div>
            <!-- ================main========================= -->
        </div>
    </div>

    <%@ include file="../common/bottom.jsp" %>
    <script>
        CKEDITOR.replace('content', {
            filebrowserImageUploadUrl: '/bbs/board/imgUpload',
            filebrowserUploadMethod: 'form',
            height:400, width:800,
        });
    </script>
</body>
</html>