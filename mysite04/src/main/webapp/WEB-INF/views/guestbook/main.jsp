<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	pageContext.setAttribute("newline","\n");
%>
<!-- 근데 지원을 개행(\n)을 지원안해줌. 그래서 스크립트를 사용해야됨 -->
<%-- <c:set var="newline" value="\n" /> --%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="guestbook">
				<form action="${pageContext.request.contextPath}/guestbook/add" method="post">
					<table border=1 width=500>
						<tr>
							<td>이름</td>
							<td><input type="text" name="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="contents" cols=60 rows=5></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<br>
					<table width=510 border=1>
						<c:set var="count" value="${fn:length(list) }" />
						<c:forEach items="${list }" var ="vo" varStatus="status">
							<tr>
								<td>[${count - status.index }]</td>
								<td>${vo.name }</td>
								<td>${vo.regDate}</td>
								<td><a
									href="${pageContext.request.contextPath}/guestbook/deleteform/${vo.no }">삭제</a></td>
							</tr>
							<tr>
								<td colspan=4>
									${fn:replace(vo.contents, newline , "<br>") }
								</td>
							</tr>
	
						
						</c:forEach>
					</table>
				<br>
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
        <c:import url="/WEB-INF/views/includes/footer.jsp"/>



	</div>
</body>
</html>