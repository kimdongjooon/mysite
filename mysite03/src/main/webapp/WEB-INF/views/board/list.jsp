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
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath}/board" method="post">
					<input type="hidden" name="p" value ="1"> 
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${pagevo.totalBoard }" />
					<c:forEach items="${list }" var="vo" varStatus="status" >
					<tr>
						<td>[${count - status.index-(pagevo.currentPage-1)*5}]</td>
						<td style = "padding-left: ${(vo.depth-1)*30}px">
							
							<c:if test="${vo.depth>=2 }">
								<img src="${pageContext.request.contextPath}/assets/images/reply.png" />
							</c:if>
							<a href="${pageContext.request.contextPath}/board?a=view&no=${vo.no}&hit=1&kwd=${param.kwd}">${vo.title }</a>
						</td>
						<td>${vo.name }</td>
						<td>${vo.hit }</td>
						<td>${vo.reg_date }</td>
						<c:if test='${vo.user_no == authUser.no}'>
							<td><a href="${pageContext.request.contextPath}/board?a=delete&no=${vo.no}&kwd=${param.kwd}" class="del">삭제</a></td>
						</c:if>
					</tr>
					</c:forEach>
							
				</table>
					<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${pagevo.currentPage !=1 }">
							<li>
								<a href="${pageContext.request.contextPath}/board?p=${pagevo.prevPage}&kwd=${param.kwd}">◀</a>
							</li>
						</c:if>
						<c:forEach begin="${pagevo.startPage }" end="${pagevo.endPage }" step="1" var="i">
							<c:if test="${pagevo.currentPage == i }">
							<li class="selected">
							</c:if>
							<c:if test="${pagevo.currentPage != i }">
							<li class=>
							</c:if>
								<a href="${pageContext.request.contextPath}/board?p=${i }&kwd=${param.kwd}">${i }</a>
							</li>
						</c:forEach>
						<c:if test="${pagevo.currentPage != pagevo.totalPage }">
							<li>
							<a href="${pageContext.request.contextPath}/board?p=${pagevo.nextPage}&kwd=${param.kwd}">▶</a>
							</li>
						</c:if>
						<%-- <li>
							<a href="${pageContext.request.contextPath}/board?p=1">1</a>
						</li>
						<li class="selected">
							<a href="${pageContext.request.contextPath}/board?p=2">2</a>
						</li>
						<li>
						<a href="${pageContext.request.contextPath}/board?p=3">3</a>
						</li>
						<li>
						<a href="${pageContext.request.contextPath}/board?p=4">4</a>
						</li>
						<li>
						<a href="${pageContext.request.contextPath}/board?p=5">5</a>
						</li> --%>
						
					</ul>
				</div>					
					<!-- pager 추가 -->
				<c:if test='${not empty authUser.no}'>
					<div class="bottom">
						<a href="${pageContext.request.contextPath}/board?a=writeform&kwd=${param.kwd}" id="new-book">글쓰기</a>
					</div>
				</c:if>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
        <c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>