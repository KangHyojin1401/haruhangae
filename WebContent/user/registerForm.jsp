<%@page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>사용자 관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet href="<c:url value='/css/user.css' />"
	type="text/css">
<script>
function userCreate() {
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
   if (form.password.value != form.password2.value) {
      alert("비밀번호가 일치하지 않습니다.");
      form.password2.focus();
      return false;
   }
   if (form.alias.value == "") {
      alert("별명을 입력하십시오.");
      form.alias.focus();
      return false;
   }
   var emailExp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
   if(emailExp.test(form.email.value)==false) {
      alert("이메일 형식이 올바르지 않습니다.");
      form.email.focus();
      return false;
   }
   var phoneExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
   if(phoneExp.test(form.phone.value)==false) {
      alert("전화번호 형식이 올바르지 않습니다.");
      form.phone.focus();
      return false;
   }
   form.submit();
}

function cancel(targetUri) {
   form.action = targetUri;
   form.submit();
}

</script>
<style>

	table {
		margin: auto;
	}
	input[type=button] {
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
</style>
</head>
<body bgcolor=#FFFFFF text=#000000 leftmargin=0 topmargin=0
	marginwidth=0 marginheight=0>
	<br>
	<!-- registration form  -->
	<form name="form" method="POST"
		action="<c:url value='/user/register' />">
		<table style="width: 100%" align="center">
			<tr>
				<td width="20"></td>
				<td>
					<table align="center">
						<tr>
							<td style="border-radius:20px;color: white;" bgcolor="#6a60a9" height="22">&nbsp;&nbsp;<b>회원 가입</b>&nbsp;&nbsp;
							</td>
						</tr>
					</table> <!-- 회원가입이 실패한 경우 exception 객체에 저장된 오류 메시지를 출력 --> 
					<c:if
						test="${registerFailed}">
						<font color="red"><c:out value="${exception.getMessage()}" /></font>
					</c:if> <br>
					<table style="background-color: #dedcee" align="center">
						<tr height="40">
							<td width="150" align="center" bgcolor="#dedcee">사용자 ID</td>
							<td width="250" bgcolor="ffffff" style="padding-left: 10"><input
								type="text" style="width: 240;" name="userID"></td>
						</tr>
						<tr height="40">
							<td width="150" align="center" bgcolor="#dedcee">비밀번호</td>
							<td width="250" bgcolor="ffffff" style="padding-left: 10"><input
								type="password" style="width: 240" name="password"></td>
						</tr>
						<tr height="40">
							<td width="150" align="center" bgcolor="#dedcee">비밀번호 확인</td>
							<td width="250" bgcolor="ffffff" style="padding-left: 10"><input
								type="password" style="width: 240" name="password2"></td>
						</tr>
						<tr height="40">
							<td width="150" align="center" bgcolor="#dedcee">별명</td>
							<td width="250" bgcolor="ffffff" style="padding-left: 10"><input
								type="text" style="width: 240" name="alias"
								<c:if test="${registerFailed}">value="${user.alias}"</c:if>>
							</td>
						</tr>
						<tr height="40">
							<td width="150" align="center" bgcolor="#dedcee">이메일 주소</td>
							<td width="250" bgcolor="ffffff" style="padding-left: 10"><input
								type="text" style="width: 240" name="email"
								<c:if test="${registerFailed}">value="${user.email}"</c:if>>
							</td>
						</tr>
						<tr height="40">
							<td width="150" align="center" bgcolor="#dedcee">전화번호</td>
							<td width="250" bgcolor="ffffff" style="padding-left: 10"><input
								type="text" style="width: 240" name="phone"
								<c:if test="${registerFailed}">value="${user.phone}"</c:if>>
							</td>
						</tr>
					</table> <br>
					<table align="center" style="width: 100%">
						<tr>
							<td style="text-align:right; width: 55%;"><input type="button" value="회원 가입"
								onClick="userCreate()"> &nbsp; </td><!-- 여기는 method="GET"인 form을 새로 만들어서 "/user/login"으로 바꿀건지 상의해보자 너무 login페이지로 향하는 url이 중구난방인 것 같음 -->
							<td><input type="button" onClick="cancel('<c:url value='/' />')" value="취소" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>