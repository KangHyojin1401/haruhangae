<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>로그인 폼</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script>
	$(document).ready(function() {
		if (request.getAttribute("registered")) {
			alert("회원 가입이 완료되었습니다.");
			request.setAttribute("registered", false);
		}
	});

	function login() {
		if (form.userID.value == "") {
			alert("사용자 ID를 입력하십시오.");
			form.userID.focus();
			return false;
		}
		if (form.password.value == "") {
			alert("비밀번호를 입력하십시오.");
			form.password.focus();
			return false;
		}
		form.submit();
	}
</script>
<style>
	div {
		/*border: 1px solid black;*/
		
	}
	.container {
		width: 90%;
		margin: auto;
	}
	
	#logo_div {
		text-align: center;
	}
	
	#logo_img {
		width: 40%;
	}
	
	#table_div {
		padding: 20% 0% 20% 0%;
		width: 70%;
		margin: auto;
		border: 5px solid #6a60a9;
		border-radius: 20px;
	}
	
	input[type=submit], input[type=button] {
		background-color: transparent;
		border-radius: 20px;
		padding: 5px 10px 5px 10px;
		background-color: #6a60a9;
		color: white;
		font-size: medium;
		font-size: 80%;
		border: 0px;
		box-shadow: none;
	}	
	
	table {
		margin: auto;
	}
	
	#id_td {
		width: 40%;
	}
	
	#login_btn_div, #register_btn_div {
		text-align: center;
	}

</style>
</head>
<body>
<div class="container">
	<div id="logo_div">
		<img id="logo_img" src="<c:url value='/images/logo_purple.png'/>"/>
	</div>
	<div id="login_form">
		<form method="POST" action="<c:url value='/user/login'/>">
			<c:if test="${loginFailed}">
				<br>
				<font color="red"><c:out value="${exception.getMessage()}" /></font>
				<br>
			</c:if>
			<div id="table_div">
				<table>
					<tr>
						<td id="id_td">아이디</td>
						<td><input type="text" name="userID"></td>
					</tr>
					<tr>
						<td>패스워드</td>
						<td><input type="password" name="password"></td>
					</tr>
				</table>
			</div>
			<br>
			<br>
			<div id="login_btn_div">
				<input id="login_btn" type="submit" onclick="login()" value="로그인">
			</div>
		</form>
				
		<form method="GET" action="<c:url value='/user/register'/>">
			<div id="register_btn_div">
				<input id="register_btn" type="button" onclick="submit()" value="회원가입">
			</div>
		</form>
	</div>
</div>
</body>
</html>