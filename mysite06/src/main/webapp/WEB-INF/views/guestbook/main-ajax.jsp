<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax
.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
// 방명록 리스트 불러와서 모두 추가하기.
var pagecount=0;
var render = function(vo, mode){
	var html = 
		"<li data-no='"+vo.no+"'>"+
		"<strong>"+vo.name+" ("+vo.regDate+")</strong>"+
		"<p>"+vo.contents+"</p>"+
		"<strong></strong>"+
		"<a href='#' data-no='"+vo.no+"'>삭제</a>"+ 
		"</li>"
		
	$("#list-guestbook")[mode ? 'prepend':'append'](html);
}

var fetch = function(n){
	$.ajax({
		url: "${pageContext.request.contextPath}/api/guestbook",
		type: "get",
		dataType: "json",
		success: function(response){
			if(response.result === 'fail'){
				console.error(response.message);
				return;
			}
			
			let i = 10*n;
			if(i<=response.data.length){
				for( i; i < response.data.length; i++){
					console.log(response.data[i]);
					render(response.data[i], false);
				}
			}
			
			/* response.data.forEach(function(vo){
				render(vo, false);
			}) */
		}
	})	
}

//최초 리스트 가져오기.
$(function(){
	fetch(pagecount);
})

//새로운 방명록 방문자 추가. 
$(function(){
	$('#add-form').submit(function(event){
		event.preventDefault();
		
		var vo ={};
		vo.name = $("#input-name").val();
		vo.password = $("#input-password").val();
		vo.contents = $("#tx-content").val();
		
		$.ajax({
			url: '${pageContext.request.contextPath}/api/guestbook',
			type: 'post',
			dataType: 'Json',
			contentType: 'application/json',
			data: JSON.stringify(vo),
			success: function(response){
				if(response.result === 'fail'){
					console.error(response.message);
					return;
				}
				$("#input-name").val("");
				$("#input-password").val("");
				$("#tx-content").val("");
				render(response.data, true);
				
				console.log(response);
			}
			
		})	
	})

});

$(function(){
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		model: true,
		buttons:{
			'삭제': function(){
				var vo ={};
				vo.no = $("#hidden-no").val();
				vo.password = $("#password-delete").val();
				console.log(vo);
				
				// 후처리.
				// 1. response.data 가지고 있는 <li data-no='{no}' > 찾아서 삭제.
				// 2. dialogDelete.dialog('close');
				$.ajax({
					url: '${pageContext.request.contextPath}/api/guestbook',
					type: 'delete',
					dataType: 'Json',
					contentType: 'application/json',
					data: JSON.stringify(vo),
					success: function(response){
						if(response.result === 'fail'){
							console.error(response.message);
							return;
						}
						
						if(response.data == true){
							$("li[data-no="+vo.no+"]").remove();
							dialogDelete.dialog("close");
						}
						if(response.data == false){
							$("p.validateTips.error").css("display","block");
						}
						
						//render(response.data, true);
						
					}
					
				})
				// $(this).dialog("ajax 삭제", no, password);
				
				
				
				// 폼의 input value reset;
			},
			'취소': function(){
				$(this).dialog("close");
			}
		},
		close: function(){
			console.log("dialog close");
		}
	});
	
	
	// 메시지 삭제 버튼 click 이벤트 처리(Live Event)
	$(document).on('click','#list-guestbook li a',function(event){
		event.preventDefault();
		
		$("#hidden-no").val($(this).attr('data-no'));
		
		console.log($(this).attr('data-no'));
		dialogDelete.dialog('open');
	});

	
});

// 스크롤기능 추가. 
$(function(){
	$(window).scroll(function(){
		
		var $window = $(this);
		var $document = $(document);
		
		var wh = $window.height();
		var dh = $document.height();
		var st = $window.scrollTop();
		
		console.log("wh + st : "+(wh + st)+ "("+wh +":"+st+")");
		console.log("dh: "+dh);
		if(dh <= wh + st+ 0.5){
			// 제일 밑으로 내려왔을때 내용 10개 더 추가.
			pagecount++;
			fetch(pagecount);
			console.log("fetch!!");
			
		}
	});

});
</script>

</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="${pageContext.request.contextPath}/api/guestbook" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">
				
				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
  				<p class="validateTips error" style="display:none" >비밀번호가 틀립니다.</p>
  				
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>