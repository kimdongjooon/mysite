<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.poscodx.mysite.vo.SiteVo" %>
<%@ page import = "org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import = "com.poscodx.mysite.service.SiteService" %>
<%-- <%!
@Autowired
private SiteService siteService;
%>
	
<%		
	SiteVo siteVo = siteService.getSite();
%> --%>
<div id="header">
	<h1>mysite</h1>
	<ul>
		<c:choose>
			<c:when test="${empty authUser }">
				<li><a href="${pageContext.request.contextPath}/user/login">로그인</a><li>
		   		<li><a href="${pageContext.request.contextPath}/user/join">회원가입</a><li>
			</c:when>
			<c:otherwise>
				<li><a href="${pageContext.request.contextPath}/user/update">회원정보수정</a><li>
				<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a><li>
				<li>${authUser.name}님 안녕하세요 ^^;</li>
			</c:otherwise>
		</c:choose>
		
	</ul>
</div>