<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style>
* { -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    -o-box-sizing: border-box;
    -ms-box-sizing: border-box;
    box-sizing: border-box; }
    
#guestbook h1 { 
	background: url("${pageContext.request.contextPath }/assets/images/guestbook.png") left 2px no-repeat;
	padding-left: 40px;
	background-size: 30px;	
	
}

#add-form input, textarea {
	display: block
	
}

#add-form input {
	margin-top: 5px;
	width: 100%;
	padding: 8px;
}

#add-form textarea {
	margin-top: 10px;
	width: 100%;
	height: 100px;
	padding: 8px;
}

#add-form input[type='submit'] {
	width: 100%;
}

#list-guestbook {
	border-top: 1px solid #999;
	width: 100%;
}

#list-guestbook li strong {
	padding-left:40px;
	font-family: "맑은 고딕";
	color: #999;
}

#list-guestbook li p {
	padding-top:10px;
	padding-left: 50px;
	font-family: "맑은 고딕";
	color: #333;
}

#list-guestbook li {
	position:relative;
	background: url("${pageContext.request.contextPath }/assets/images/user.png") left 15px no-repeat;
}

#guestbook ul li a {
	width:30px;
	height:30px;
	font-size:0%;
	position:absolute;
	background: url("${pageContext.request.contextPath }/assets/images/delete.png") left no-repeat;
	left:20px;
	top:30px;
}

input[type=submit] {
	-webkit-appearance:none;
	background-color: #fff;
}

</style>
<script>

var isEnd = false;
var messageBox = function( title, message, callback ) {
	// attr:  HTML 의 속성(attribute)을 취급
	// HTML attribute 값이 모두 String 으로 넘어옴
	$( "#dialog-message" ).attr( "title", title );
	$( "#dialog-message p" ).text( message );
	
	$( "#dialog-message" ).dialog({
		modal: true,
		buttons: {
			 "확인": function() {
				$( this ).dialog( "close" );
			}
		},
		close: callback || function(){}
	});	
}
		
var render = function( mode, vo ) {
	var html = 
			   "<li data-no='" + vo.no + "'>" + 
			   "<strong>" + vo.name + "</strong>" +
			   "<p>" + vo.content.replace(/\n/gi, "<br>") +  "</p>" +
			   "<strong></strong>" +
			   "<a href='#' data-no='" + vo.no + "'>삭제</a>" +
			   "</li>";
	if( mode == true ) {
		$("#list_guestbook").prepend( html );
	} else {
		$("#list-guestbook").append( html );
	}
}
$(function(){
	
	//삭제시 비밀번호 입력 모달 다이알로그
	var deleteDialog = $( "#dialog-delete-form" ).dialog({
		autoOpen: false,
		modal: true,
		buttons: {
			"삭제": function(){
					var password = $("#password-delete").val();
					var no = $("#hidden-no").val();
					  			
	  			// ajax 통신
		  			$.ajax({
		  				url: "/mysite3/api/guestbook/delete",
		  				type: "post",
		  				dataType: "json",
		  				data:"no=" + no + "&password=" + password,
		  				success: function(response){
		  					if( response.result == "fail" ) {
		  						console.log( response.message );
		  						return;
		  					}
					  					
		  					if( response.data == -1 ) {
		  						$(".validateTips.normal").hide();
		  						$(".validateTips.error").show();
		  						$("#password-delete").val("");
	
		  						return;
		  					}
					  				
					  		$("#list-guestbook li[data-no=" + response.data + "]").remove();
					 			deleteDialog.dialog( "close" );
					 		}
		  			});
		  		},
	        "취소": function() {
		          	deleteDialog.dialog( "close" );
		          }
		        },
		close: function() {
					$("#password-delete").val( "" );
		  			$("#hidden-no").val( "" );
	  				$(".validateTips.normal").show();
					$(".validateTips.error").hide();
		}
	});
	
	//Live Event Listener
	$(document).on( "click", "#list-guestbook li a", function(event){
		// http://ismydream.tistory.com/98 버튼클릭시 브라우저가 이동하지는않지만 스크롤 최상단으로 이동하는걸 방지
		event.preventDefault();
		
		var no = $(this).data("no");
		$("#hidden-no").val(no);
		deleteDialog.dialog( "open" );
	});
	
	// 보내기버튼 클릭시
	$("#add-form").submit( function(event) {
		// http://ismydream.tistory.com/98 버튼클릭시 브라우저가 이동하지는않지만 스크롤 최상단으로 이동하는걸 방지
		event.preventDefault();

	
		var data = {};
		// serializeArray() 전송 형태 : [ { name : "a", value : "1" }, { name : "b", value : "2" } ]
		// serialize() 전송 형태 : a=1&b=2&c=3
		$.each($(this).serializeArray(), function(index, o){
			data[o.name] = o.value;
		});
		
		if( data["name"] == '' ) {
			messageBox( "메세지 등록", "이름이 비어 있습니다." );
			$("#input-name").focus();
			return;
		}
		
		if( data["password"] == '' ) {
			messageBox( "메세지 등록", "비밀번호가  비어 있습니다.", function(){
				$("input-password").focus();
			} );
			return;
		}
		
		if( data["content"] == '' ) {
			messageBox( "메세지 등록", "내용이 비어 있습니다.", function(){
				$("#tx-content").focus();
			} );
			return;
		}
		
		$.ajax({
			url: "/mysite3/api/guestbook/insert",
			type: "post",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify( data ),
			success: function( response ) {
				render( true, response.data );
				$("#add-form")[0].reset();	
			}
		}); 
	});	
	
	$("#btn-fetch").click(function(){
		if( isEnd == true ) {
			return;
		}
		
		var startNo = $( "#list-guestbook li" ).last().data("no") || 0;
		$.ajax({
			url: "/mysite3/api/guestbook/list?no=" + startNo,
			type: "get",
			dataType: "json",
			success: function( response ){
				//성공 유무
				if( response.result != "success" ) {
					render( true, response.data );
					$("#add-form")[0].reset();
				}
				
				// 끝 감지
				if( response.data.length < 5 ) {
					isEnd = true;
					// prop: js의 프로퍼티(js에서 사용하는 정보)를 취급. 
					// 자바스크립트의 프로퍼티 값이 넘어오기 때문에 boolean, date, function 등도 가져올 수 있음
					$("#btn-fetch").prop( "disabled", true );
				}
				
				// render
				$.each( response.data, function( index, vo ){
					render( false,vo );
			  	});
				
				var length = response.data.length;
				if ( length > 0 ) {
					startNo = response.data[ length - 1 ].no;
				}
			}
		});
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
				<form id="add-form" action="" method="post">
					<input type="text" name="name" id="input-name" placeholder="이름">
					<input type="password" name="password" id="input-password" placeholder="비밀번호">
					<textarea name="content" id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook"></ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			
			<button id="btn-fetch">가져오기</button>
 
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