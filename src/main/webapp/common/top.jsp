<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
	<div class="container-fluid">
       <ul class="navbar-nav">
           <a class="navbar-brand ms-5 me-5" href="#">
               <img src="/bbs/img/ckworld-logo.png" alt="Logo" style="height:36px;" class="rounded-3">
           </a>
           <li class="nav-item">
               <a class="nav-link" href="#"><i class="fa-solid fa-house"></i> Home</a>
           </li>
           <li class="nav-item ms-3">
               <a class="nav-link ${(menu eq 'board') ? 'active' : 'null'}" href="/bbs/board/list?page=1"><i class="far fa-list-alt"></i> 게시판</a>
           </li>
           <li class="nav-item ms-3">
               <a class="nav-link ${(menu eq 'user') ? 'active' : 'null'}" href="/bbs/user/list?page=1"><i class="fas fa-user-friends"></i> 사용자</a>
           </li>
           <li class="nav-item ms-3">
           	<c:if test="${empty sessionScope.uid}">
               <a class="nav-link ${(menu eq 'login') ? 'active' : 'null'}" href="/bbs/user/login"><i class="fas fa-sign-out-alt"></i>로그인</a>
           	</c:if>
           	<c:if test="${not empty sessionScope.uid}">
               <a class="nav-link" href="/bbs/user/logout"><i class="fas fa-sign-out-alt"></i>로그아웃</a>
           	</c:if>
           </li>
       </ul>
       <c:if test="${not empty sessionScope.uid}">
       <span class="navbar-text me-3">${uid}님 환영합니다.</span>
       </c:if>
   </div>
</nav>