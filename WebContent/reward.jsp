<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function setTitle() {
		$("#page_name").text("REWARD");
	}
</script>
<style type="text/css">
#reward_table_div {
	text-align: center;
}

table {
	margin: auto;
	width: 100%;
}

/*
table td {
	border: hidden;
}*/

#reward {
	width: 100%;
	height: 100px;
	margin: auto;
	text-align: left;
}

#reward_img {
	height: 100px;
	width: 100px;
}

#menu {
	text-align: right;
}

.container {
	width: 90%;
	margin: auto;
}
	
#arrow_div{
	text-align: left;
}

h3 {
	text-align: center;
}

.reward_img_div {
	width: max-content;
	margin: auto;
}

hr {
	border: 1px solid #6a60a9;
	background-color: #6a60a9;
	border-radius: 20px;
}

#date {
	color: gray;
	font-size: 80%;
}

</style>
<meta charset="UTF-8">
<title>Reward</title>
</head>
<body onload="setTitle();">
<div class="container">
	<%@ include file="/topBar.jsp"%>
	<br> <br> <br> <br>
	
	<c:if test="${not empty rewardList}">
	<div id="reward_table_div">
	<table id='reward'>
		<c:forEach var="reward" items="${rewardList}">
			<tr>
				<td rowspan="4">
					<div class="reward_img_div">
						<img id="reward_img" src="${reward.iconAddr}" alt="">
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<h2>${reward.name}</h2>
				</td>
			</tr>
			<tr>
				<td>${reward.content}</td>
			</tr>
			<tr>
				<td><span id="date">획득 날짜: ${reward.achievement}</span></td>
			</tr>
			<tr>
				<td colspan='2'>
					<hr>
				</td>
			</tr>
			
		</c:forEach>
	</table>
	</div>
	
	</c:if>
	<c:if test="${empty rewardList}">
		<br><br><br><br><br>
		<h3>아직 획득한 리워드가 없습니다!</h3>
	</c:if>
</div>
</body>
</html>