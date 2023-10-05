<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board" class="board-form">
				<p>조회수 ${boardvo.hit }회</p>
				<table class="tbl-ex">
					<tr>
						<th colspan="2" align="right">글보기</th>
						
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${boardvo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${boardvo.contents }<br>
								
							</div>
						</td>
					</tr>
				</table>
				
				<c:if test='${mode == "review" }' >
					<div id="board">
					<form class="board-form" method="post" action="${pageContext.request.contextPath}/board/review/${authUser.no}">
						<input type = "hidden" name = "g_no" value="${boardvo.g_no }">
						<input type = "hidden" name = "o_no" value="${boardvo.o_no }">
						<input type = "hidden" name = "depth" value="${boardvo.depth }">
						<table class="tbl-ex">
							<tr>
								<th colspan="2">댓글달기</th>
							</tr>
							<tr>
								<td class="label">제목</td>
								<td><input type="text" name="title" value=""></td>
							</tr>
							<tr>
								<td class="label">내용</td>
								<td>
									<textarea id="content" name="contents"></textarea>
								</td>
							</tr>
						</table>
						<div class="bottom">
							<a href="${pageContext.request.contextPath}/board/${pagevo.currentPage}">글목록</a>
							<a href="${pageContext.request.contextPath}/board/view/${boardvo.no}">취소</a>
							<input type="submit" value="등록">
						</div>
					</form>				
					</div>
				</c:if>
				<c:if test='${mode != "review" }' >
				<div class="bottom">
					<a href="${pageContext.request.contextPath}/board/${pagevo.currentPage}">글목록</a>
					<c:if test='${not empty authUser.no }'>
						<a href="${pageContext.request.contextPath}/board/review/${boardvo.no}">댓글달기</a>
						<c:if test='${boardvo.user_no == authUser.no}'>
							<a href="${pageContext.request.contextPath}/board/modify/${boardvo.no}">글수정</a>
						</c:if>
					</c:if>
				</div>
				</c:if>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
        <c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>