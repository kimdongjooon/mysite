<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">

				<form:form
					modelAttribute="userVo"
					id="join-form" 
					name="joinForm" 
					method="post" 
					action="${pageContext.request.contextPath}/user/join">
					
					<!-- <input type = "hidden" name = "a" value ="join"> 복습하면서 지우기. -->
					<label class="block-label" for="name">이름</label>
					
					<form:input path="name"/>
					
					<%-- <input id="name" name="name" type="text" value="${userVo.name }"> --%>
					<p style= "padding:3px 0 5px 0; text-align: left; color: #f00">
						<spring:hasBindErrors name="userVo">
							<c:if test="${errors.hasFieldErrors('name') }">
								<%-- ${errors.getFieldError('name').defaultMessage } --%>
								<spring:message code="${errors.getFieldError('name').codes[0] }" />
							</c:if>
						</spring:hasBindErrors>
					</p>
					
					
					<label class="block-label" for="email">이메일</label>
					<form:input path="email"/>
					<%-- <input id="email" name="email" type="text" value="${userVo.email }"> --%>
					<input type="button" value="id 중복체크">
					<p style= "padding:3px 0 5px 0; text-align: left; color: #f00">
						<form:errors path="email"/>
					</p>
					<!-- <a href="#" id="openPopupLink" text-decoration="none" background-color= "#FF4500">팝업 열기 </a>

						    <script>
						        document.getElementById('openPopupLink').addEventListener('click', function (e) {
						            e.preventDefault(); // 링크의 기본 동작을 중지합니다.
						
						            // 팝업 창을 열기 위한 코드
						            // 여기에 팝업 창을 생성하고 내용을 추가하는 로직을 작성할 수 있습니다.
						            var popup = window.open('', '팝업 창', 'width=200,height=100');
						
						            // 팝업 창에 내용 추가 예제
						            popup.document.write('<h1>안녕하세요!</h1>');
						            popup.document.write('<p>팝업 창 내용입니다.</p>');
						
						            // 팝업 창 스타일 설정 예제
						            popup.document.body.style.backgroundColor = 'lightblue';
						
						            // 팝업 창을 닫을 수 있는 버튼 추가 예제
						            popup.document.write('<button onclick="window.close()">닫기</button>');
						        });
						    </script> -->
  					
					
					<label class="block-label"><spring:message code="user.join.label.password" /></label>
					<form:password path="password" />
					<%-- <input name="password" type="password" value="${userVo.password }"> --%>
					<p style= "padding:3px 0 5px 0; text-align: left; color: #f00">
						<form:errors path="password"/>
					</p>
					
					
					<fieldset>
						<legend>성별</legend>
						<form:radiobutton path="gender" value="female" label ="여" checked="checked"/>
						<form:radiobutton path="gender" value="male" label ="남" checked="checked"/>
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
        <c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>

<script>
</script>



