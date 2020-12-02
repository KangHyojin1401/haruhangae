<%@page contentType="text/html; charset=utf-8"%>
<%@page import="model.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
   UserDTO user = (UserDTO)request.getAttribute("user");
%>
<html>
<head>
<title>사용자 관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet href="<c:url value='/css/user.css' />"
	type="text/css">
<script>
function userModify() {
   if (form.password.value == "") {
      alert("비밀번호를 입력하십시오.");
      form.password.focus();
      return false;
   }
   if (form.password.value != form.password2.value) {
      alert("비밀번호가 일치하지 않습니다.");
      form.name.focus();
      return false;
   }
   if (form.name.value == "") {
      alert("별명을 입력하십시오.");
      form.name.focus();
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

function goBack(targetUri) {
   form.action = targetUri;
   form.submit();
}
</script>
<style>
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
	<!-- Update Form  -->
	<form name="form" method="POST" action="<c:url value='/user/update' />">
		<input type="hidden" name="userID" value="${user.userID}" />
		<table style="width: 100%">
			<tr>
				<td width="20"></td>
				<td>
					<table align="center">
						<tr>
							<td style="border-radius:20px;color: white;" bgcolor="#6a60a9"  height="22">&nbsp;&nbsp;<b>회원 정보 수정</b>&nbsp;&nbsp;
							</td>
						</tr>
					</table> <br>
					<table style="background-color: #dedcee" align="center">
						<tr height="40">
							<td width="150" align="center" bgcolor="#dedcee">사용자 ID</td>
							<td width="250" bgcolor="ffffff" style="padding-left: 10">
								${user.userID}</td>
						</tr>
						<tr height="40">
							<td width="150" align="center" bgcolor="#dedcee">비밀번호</td>
							<td width="250" bgcolor="ffffff" style="padding-left: 10"><input
								type="password" style="width: 240" name="password"
								value="${user.password}"></td>
						</tr>
						<tr height="40">
							<td width="150" align="center" bgcolor="#dedcee">비밀번호 확인</td>
							<td width="250" bgcolor="ffffff" style="padding-left: 10"><input
								type="password" style="width: 240" name="password2"
								value="${user.password}"></td>
						</tr>
						<tr height="40">
							<td width="150" align="center" bgcolor="#dedcee">별명</td>
							<td width="250" bgcolor="ffffff" style="padding-left: 10"><input
								type="text" style="width: 240" name="alias"
								value="${user.alias}"></td>
						</tr>
						<tr height="40">
							<td width="150" align="center" bgcolor="#dedcee">이메일 주소</td>
							<td width="250" bgcolor="ffffff" style="padding-left: 10"><input
								type="text" style="width: 240" name="email"
								value="${user.email}"></td>
						</tr>
						<tr height="40">
							<td width="150" align="center" bgcolor="#dedcee">전화번호</td>
							<td width="250" bgcolor="ffffff" style="padding-left: 10"><input
								type="text" style="width: 240" name="phone"
								value="${user.phone}"></td>
						</tr>
					</table> <br>
					<table style="width: 100" align="center">
						<tr>
							<td><input type="button" value="취소"
								onClick="goBack('<c:url value='/user/myPage' />')"></td>
							<td><input type="button" value="수정" onClick="userModify()">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>