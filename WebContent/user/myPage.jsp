<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Page</title>
<style>
	div {
		
	}
	
	#arrow_div{
		text-align: left;
	}
	
	table {
		margin: auto;
	}
	
	.container {
		width: 90%;
		margin: auto;
	}
	
	.big {
		font-size: 20pt;
	}
	
	.middle {
		font-size: 15pt;
	}
	
	.small {
		font-size: 12pt;
	}
	
	.mobile-menu {
		display: block;
		position: fixed;
		top: 15px;
		left: 15px;
		z-index: 500;
		width: 45px;
		height: 45px;
		padding: 5px;
		background-color: #f0f0f0;
		border: 0;
	}
	
	.mobile-menu i {
		font-size: 2em;
	}
	
	.menuwrap {
		position: fixed;
		top: 0;
		right: -300px; /* 너비 300px 인 사이드바를 오른쪽으로 300px 이동시켜 화면에 보이지 않게 함 */
		z-index: 400;
		overflow: auto;
		width: 300px; /* 너비 */
		height: 100%;
		padding: 50px 20px;
		box-sizing: border-box;
		transition: left .3s ease-in-out; /* 0.3초 동안 이동 애니메이션 */
		background-color: #f0f0f0;
	}
	
	.menuwrap.on {
		right: 0px;
	}
	
	/*그라데이션
	#print_table_div {
	    border: 5px solid transparent;
	    border-radius: 20px;
	    background-image: linear-gradient(white, white), linear-gradient(to right, #dedcee, #6a60a9);
	    background-origin: border-box;
	    background-clip: content-box, border-box;
	    
	}*/
	
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
	
	#print_mission_div {
		background-color: #dedcee;
		padding: 5% 20% 10% 20%;
		border-radius: 20px;
		width: max-content;
		margin: auto;
	}
	
	
	p {
		text-align: center;
		font-size: 150%;
		font-weight: bolder;
		color: #6a60a9;
	}
	
	.td1 {
		padding-right: 40px;
	}
	
	.td2 {
		text-align: right;
	}

</style>
<script src="http://code.jquery.com/jquery-latest.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function setTitle() {
		$("#page_name").text("MY PAGE");
	}
	
	function checkPassword() {
	   prompt("패스워드를 입력하세요.", "");
	}
	
	function checkDelete(targetUri) {
	   if (confirm("정말로 탈퇴하시겠어요?") == true) {
	      form.action = targetUri;
	      form.submit();
	   }
	}
	
	function updateUserInfo() {
	   form.submit();
	}
	
	function slideMenu() {
	   if (document.querySelector('.menuwrap').classList.contains('on') ){
	        //메뉴닫힘
	        document.querySelector('.menuwrap').classList.remove('on');
	    } else {
	        //메뉴펼처짐
	        document.querySelector('.menuwrap').classList.add('on');
	    }
	}

</script>
</head>
<body onload="setTitle();">
<div class="container">
	<%@ include file="/topBar.jsp"%>			
	<br> <br> <br> <br> 
	
	<div id="print_table_div">
		<div id="print_mission_div">
			<p id="alias">${user.alias} 님</p>
		
			<br>
		
			<table align="center">
				<tr class="small">
					<td class="td1">달성 미션 수</td>
					<td class="td2">${user.totalMission}개</td>
				</tr>
				<tr class="small">
					<td class="td1">수집한 뱃지 수</td>
					<td class="td2">${user.totalReward}개</td>
				</tr>
			</table>
		</div>
	
	<br><br>
	
		<form name="form" method="GET" action="<c:url value='/user/update'/> ">
		<table>
			<tr>
				<td><input type="button" value="회원 정보 수정" onClick="updateUserInfo()" /></td>
				<td><input type="button" value="계정 탈퇴" onClick="checkDelete('<c:url value='/user/delete'/>')" /></td>
			</tr>
		</table>
		</form>
		
	</div>
</div>
</body>
</html>