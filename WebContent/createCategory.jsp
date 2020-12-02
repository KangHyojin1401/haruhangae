<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Calendar cal = Calendar.getInstance();
	String strYear = request.getParameter("year");
	String strMonth = request.getParameter("month");
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH);
	int date = cal.get(Calendar.DATE);
	
	if (strYear != null) {
		year = Integer.parseInt(strYear);
		month = Integer.parseInt(strMonth);
	}
	
	//년도/월 셋팅
	
	cal.set(year, month, 1);
	
	int startDay = cal.getMinimum(java.util.Calendar.DATE);
	int endDay = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
	int start = cal.get(java.util.Calendar.DAY_OF_WEEK);
	int newLine = 0;
	
	//오늘 날짜 저장.
	Calendar todayCal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd");
	int intToday = Integer.parseInt(sdf.format(todayCal.getTime()));
%>
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function setTitle() {
		$("#page_name").text("HOME");
	}
</script>
<style type="text/css">
/*
div {
	border: 1px solid black;
	text-align: center;
}*/

div {
}

#arrow_div{
	text-align: left;
}

.container {
		width: 90%;
		margin: auto;
	}

table td {
	border: 1px solid grey;
}

td {
	text-align: center;
	width: 11.4%;
}

table {
	width: 100%;
	height: 100px;
	margin: auto;
	border-collapse: collapse;
	border: 1px solid grey;
}

body {
	scrollbar-face-color: #F6F6F6;
	scrollbar-highlight-color: #bbbbbb;
	scrollbar-3dlight-color: #FFFFFF;
	scrollbar-shadow-color: #bbbbbb;
	scrollbar-darkshadow-color: #FFFFFF;
	scrollbar-track-color: #FFFFFF;
	scrollbar-arrow-color: #bbbbbb;
	margin-left: "0px";
	margin-right: "0px";
	margin-top: "0px";
	margin-bottom: "0px";
}

td {
	font-size: 9pt;
	color: #595959;
}

th {
	font-size: 9pt;
	color: #000000;
}

select {
	font-size: 9pt;
	color: #595959;
}

.divDotText {
	overflow: hidden;
	text-overflow: ellipsis;
}

#welcome_div, #select_info_div {
	text-align: center;
}

#select_info_div {
	font-size: 120%;
	font-weight: bolder;
	color: #50487d;
}

#select_form_div {
	width: max-content;
	margin: auto;
}

#select_btn_div {
	text-align: center;
}

#welcome_div {
	text-align: center;
	width: max-content;
	margin: auto;
	padding: 1% 2% 1% 2%;
	border-radius: 10px;
	background-color: #6a60a9;
	color: white;
	font-size: 90%;
}

#category_wrap {
	padding: 4% 5% 4% 5%;
	border: 0px solid black;
	border-radius: 10px;
	background-color: #dedcee;
}

#select_btn {
	padding: 5px 10px 5px 10px;
	font-size: small;
	background-color: transparent;
	border-radius: 20px;
	background-color: #6a60a9;
	color: white;
	border: 0px;
	box-shadow: none;
}

</style>
<meta charset="UTF-8" content="">
<title>Home</title>
</head>

<body onload="setTitle();">
<div class="container">
	<%@ include file="/topBar.jsp"%>			
	<br> <br> <br> <br> 

	<div id='challenge'>
		<div id="welcome_div">${user.alias}님 환영합니다. </div>
	
		<div id="category_wrap">
			<div id="select_info_div">카테고리를 선택해주세요</div>
			<div id="select_form_div">
			<br>
				<form name="form" method="POST" action="<c:url value='/createCategory'/>">
					<c:forEach var="category" items="${categoryList}">
						<input type="radio" id="${category.catID}" name="category" value="${category.catID}">
							<label for="${category.catID}">${category.catName}</label><br />
					</c:forEach>
					<br> 
					<div id="select_btn_div"><input id="select_btn" type="submit" value='선택'></div>
				</form>
			</div>
		</div>
	</div>
	<br />
	<DIV id="content" style="text-align: center;">

		<!--날짜 네비게이션  -->
		<table width="100%" border="0" cellspacing="1" cellpadding="1"
			id="KOO" bgcolor="#fffcf0" style="border: 1px solid #CED99C">
			<tr>
				<td height="60">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						style="border: hidden; border-top: hidden;">
						<tr>
							<td height="10"></td>
						</tr>
						<tr>
							<td align="center"><a
								href="<c:url value='/home.jsp' />?year=<%=year - 1%>&amp;month=<%=month%>"
								target="_self"> <b>&lt;&lt;</b> <!-- 이전해 -->
							</a> <%
 	if (month > 0) {
 %> <a
								href="<c:url value='/home.jsp' />?year=<%=year%>&amp;month=<%=month - 1%>"
								target="_self"> <b>&lt;</b> <!-- 이전달 -->
							</a> <%
 	} else {
 %> <b>&lt;</b> <%
 	}
 %> &nbsp;&nbsp; <%=year%>년 <%=month + 1%>월 &nbsp;&nbsp; <%
 	if (month < 11) {
 %> <a
								href="<c:url value='/home.jsp' />?year=<%=year%>&amp;month=<%=month + 1%>"
								target="_self"> <!-- 다음달 --> <b>&gt;</b>
							</a> <%
 	} else {
 %> <b>&gt;</b> <%
 	}
 %> <a
								href="<c:url value='/home.jsp' />?year=<%=year + 1%>&amp;month=<%=month%>"
								target="_self"> <!-- 다음해 --> <b>&gt;&gt;</b>
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br>
		<table border="0" cellspacing="1" cellpadding="1" bgcolor="#FFFFFF">
			<THEAD>
				<TR bgcolor="#CECECE">
					<TD width='100px'>
						<DIV align="center">
							<font color="red">일</font>
						</DIV>
					</TD>
					<TD width='100px'>
						<DIV align="center">월</DIV>
					</TD>
					<TD width='100px'>
						<DIV align="center">화</DIV>
					</TD>
					<TD width='100px'>
						<DIV align="center">수</DIV>
					</TD>
					<TD width='100px'>
						<DIV align="center">목</DIV>
					</TD>
					<TD width='100px'>
						<DIV align="center">금</DIV>
					</TD>
					<TD width='100px'>
						<DIV align="center">
							<font color="#529dbc">토</font>
						</DIV>
					</TD>
				</TR>
			</THEAD>
			<TBODY>
				<TR>
					<%
						//처음 빈공란 표시
					for (int index = 1; index < start; index++) {
						out.println("<TD >&nbsp;</TD>");
						newLine++;
					}
					for (int index = 1; index <= endDay; index++) {
						String color = "";
						if (newLine == 0) {
							color = "RED";
						} else if (newLine == 6) {
							color = "#529dbc";
						} else {
							color = "BLACK";
						}
						;
						String sUseDate = Integer.toString(year);
						sUseDate += Integer.toString(month + 1).length() == 1 ? "0" + Integer.toString(month + 1)
						: Integer.toString(month + 1);
						sUseDate += Integer.toString(index).length() == 1 ? "0" + Integer.toString(index) : Integer.toString(index);
						int iUseDate = Integer.parseInt(sUseDate); //yyyymmdd 형식
						String backColor = "#fffcf0";
						if (iUseDate == intToday) {
							backColor = "#fbd14b";
						}
						out.println("<TD valign='top' align='left' height='92px' bgcolor='" + backColor + "' nowrap>");
						Object userId = session.getAttribute("userId"); //session 값 받아오기
					%>

					<c:url value="/calendar/list" var="url">
						<c:param name="year" value="<%=Integer.toString(year)%>" />
						<c:param name="month" value="<%=Integer.toString(month)%>" />
						<c:param name="day" value="<%=Integer.toString(index)%>" />
					</c:url>
					<font color='<%=color%>'> <!-- onClick은 값이 제대로 선택 됐는지 확인하기 위해! 추후 삭제해야 됨 -->
						<a href="${url}"> <%=index%></a>
					</font>
					<%
						//기능 제거
					out.println("</TD>");
					newLine++;
					if (newLine == 7) {
						out.println("</TR>");
						if (index <= endDay) {
							out.println("<TR>");
						}
						newLine = 0;
					}
					}
					//마지막 공란 LOOP
					while (newLine > 0 && newLine < 7) {
					out.println("<TD>&nbsp;</TD>");
					newLine++;
					}
					%>
				</TR>
			</TBODY>
		</TABLE>
	</DIV>
	</div>
</div>
</body>
</html>