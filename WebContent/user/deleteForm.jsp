<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴</title>
<style>
.middle {
	font-size: 15pt;
}

.small {
	font-size: 12pt;
}
</style>
<script type="text/javascript">

function goBack(targetUri) {
   form.action = targetUri;
   form.submit();
}
</script>
</head>
<body>
	<%@ include file="/topBar.jsp"%>			
	<br> <br> <br> <br> 
	<form name="form" method="POST" action="<c:url value='/user/delete'/> ">
		<table align="center">
			<tr class="middle">
				<td align="center">회원 탈퇴</td>
			</tr>
			<tr>
				<td><br /></td>
			</tr>
			<tr>
				<td><br /></td>
			</tr>
			<tr>
				<td><br /></td>
			</tr>
			<tr>
				<td>비밀번호를 입력하세요.</td>
			</tr>
			<tr>
				<td><br /></td>
			</tr>
			<tr>
				<td align="center"><input type="password" name="password" /></td>
			</tr>
		</table>
		<table align="center">
			<tr>
				<td><br /></td>
			</tr>
			<tr>
				<td><br /></td>
			</tr>
			<tr>
				<td align=center><input type="button" value="확인"
					onClick="submit()" /></td>
				<td align=center><input type="button" value="취소"
					onClick="goBack('<c:url value='/user/myPage'/>')" /></td>
			</tr>
		</table>
	</form>
</body>
</html>