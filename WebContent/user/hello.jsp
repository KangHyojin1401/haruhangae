<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>회원 가입 완료</title>
<style>
a {
	color: black;
	display: block;
	text-decoration: none;
	background: #cccccc;
}

span {
	text-align: center;
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
<body>
	<form action="<c:url value='/user/login' />">
		<table align="center">
			<tr>
				<td align="center">환영해요.</td>
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
				<td><span>회원 가입이 완료되었습니다.<br>로그인 후 서비스를 이용해주세요.<span></span></td>
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
				<td><br /></td>
			</tr>
			<tr>
				<td align="center"><input type="button" value="로그인 페이지로 이동하기"
					onClick="submit()" /></td>
			</tr>
		</table>
	</form>
</body>
</html>