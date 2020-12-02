<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
   Calendar cal = Calendar.getInstance();
   String strYear = request.getParameter("year");
   String strMonth = request.getParameter("month");
   int year = cal.get(Calendar.YEAR);
   int month = cal.get(Calendar.MONTH);
   int date = cal.get(Calendar.DATE);
   
   if(strYear != null)
   {
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
   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
   int intToday = Integer.parseInt(sdf.format(todayCal.getTime()));
%>

<html lang="ko">

<HEAD>
<TITLE>캘린더</TITLE>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<script type="text/javaScript" language="javascript"> 
   function getValue(date){
        alert(date);
       }
   </script>

<style TYPE="text/css">

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
   margin: auto;
   width: 100%;
   height: 100px;
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

#info {
   text-align: center;
}

#myPost_div {
   padding: 5% 5% 5% 5%;
   border: 3px solid  #6a60a9;
   border-radius: 10px;
}

.post_contents {
   padding: 3% 0% 3% 0%;
   font-size: 110%;
}

.post_tag, .post_rating {
   font-size: 90%;
}

.rating {
   color: #fbd14b;
   font-weight: bold;
}

.post_location {
   font-size: 70%;
   color: gray;
}

.date {
   font-size: 50%;
   color: gray;
}

.post_missionContent {
   text-align: center;
   font-weight: bold;
   border-top: 2px solid #6a60a9;
   border-bottom: 2px solid #6a60a9;
   padding: 3% 0% 3% 0%;
   width: max-content;
   margin: auto;
}

</style>
<script src="http://code.jquery.com/jquery-latest.js"
   type="text/javascript"></script>
<script type="text/javascript">
   function setTitle() {
      $("#page_name").text("CALENDAR");
   }
</script>
</HEAD>
<BODY onload="setTitle();">
<div class="container">
   <%@ include file="/topBar.jsp"%>         
   <br> <br> <br> <br> 
   
   <form name="calendarFrm" id="calendarFrm" action="" method="post">
      <DIV id="content" style="text-align: center;">

         <!--날짜 네비게이션  -->
         <table width="100%" border="0" cellspacing="1" cellpadding="1"
            id="KOO" bgcolor="#fffcf0" style="border: 1px solid #CED99C">
            <tr>
               <td height="60">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                     <tr>
                        <td height="10"></td>
                     </tr>
                     <tr>
                        <td align="center"><a
                           href="<c:url value='/calendarPage.jsp' />?year=<%=year-1%>&amp;month=<%=month%>"
                           target="_self"> <b>&lt;&lt;</b>
                           <!-- 이전해 -->
                        </a> <%if(month > 0 ){ %> <a
                           href="<c:url value='/calendarPage.jsp' />?year=<%=year%>&amp;month=<%=month-1%>"
                           target="_self"> <b>&lt;</b>
                           <!-- 이전달 -->
                        </a> <%} else {%> <b>&lt;</b> <%} %> &nbsp;&nbsp; <%=year%>년 <%=month+1%>월
                           &nbsp;&nbsp; <%if(month < 11 ){ %> <a
                           href="<c:url value='/calendarPage.jsp' />?year=<%=year%>&amp;month=<%=month+1%>"
                           target="_self"> <!-- 다음달 -->
                              <b>&gt;</b>
                        </a> <%}else{%> <b>&gt;</b> <%} %> <a
                           href="<c:url value='/calendarPage.jsp' />?year=<%=year+1%>&amp;month=<%=month%>"
                           target="_self"> <!-- 다음해 -->
                              <b>&gt;&gt;</b>
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
   for(int index = 1; index < start ; index++ )
   {
     out.println("<TD >&nbsp;</TD>");
     newLine++;
   }
   for(int index = 1; index <= endDay; index++)
   {
          String color = "";
          if(newLine == 0){          color = "RED";
          }else if(newLine == 6){    color = "#529dbc";
          }else{                     color = "BLACK"; };
          String sUseDate = Integer.toString(year); 
          sUseDate += Integer.toString(month+1).length() == 1 ? "0" + Integer.toString(month+1) : Integer.toString(month+1);
          sUseDate += Integer.toString(index).length() == 1 ? "0" + Integer.toString(index) : Integer.toString(index);
          int iUseDate = Integer.parseInt(sUseDate); //yyyymmdd 형식
          String backColor = "#fffcf0";
          if(iUseDate == intToday ) { // 오늘 날짜 배경 색 설정
                backColor = "#fbd14b";
          }
          %>

                  <c:set var="useDate" value="<%= sUseDate  %>" />
                  <!-- 선택한 날짜 배경 색 설정  -->
                  <c:if test="${useDate == selectedDate}">
                     <% backColor = "#dddbff"; %>
                  </c:if>


                  <%
          out.println("<TD valign='top' align='left' height='92px' bgcolor='"+backColor+"' nowrap>");
          Object userID = session.getAttribute("userID"); //session 값 받아오기
         %>

                  <c:url value="/calendar/list" var="url">
                     <c:param name="userID" value="${userID}" />
                     <c:param name="year" value="<%= Integer.toString(year) %>" />
                     <c:param name="month" value="<%= Integer.toString(month) %>" />
                     <c:param name="day" value="<%= Integer.toString(index) %>" />

                  </c:url>
                  <font color='<%=color%>'> <a href="${url}"> <%=index %></a>
                  </font>
                  <%
          //기능 제거
          out.println("</TD>");
          newLine++;
          if(newLine == 7)
          {
            out.println("</TR>");
            if(index <= endDay)
            {
              out.println("<TR>");
            }
            newLine=0;
          }
   }
   //마지막 공란 LOOP
   while(newLine > 0 && newLine < 7)
   {
     out.println("<TD>&nbsp;</TD>");
     newLine++;
   }
%>
               </TR>
            </TBODY>
         </TABLE>
      </DIV>
   </form>
   
   <br>
   <br>
      <div class="print_post" id="myPost_div">
         <c:if test="${ myPost != null }">
               <!-- <img id="my_post_img" src="<c:url value='/images/logo_square.png'/>"
                  class="post_img" alt="default image(로고)"> -->
            <div class="post_missionContent">${ myPost.missionContent }</div>
            
            <br>
            
            <div class="post_rating">만족도: <span class="rating">${ myPost.rating }</span>점</div>
            <div class="post_location">${ myPost.location }</div>
            <div class="post_contents">${ myPost.content }</div>
            <div class="post_tag">
               <c:forEach var="tag" items="${ myPost.tags }">
                  <c:if test="${ tag != null }">
                     <span>#${ tag }&nbsp;</span>
                  </c:if>
               </c:forEach>
            </div>
            <div class="date">${ myPost.date } 작성</div>
         </c:if>
         <c:if test="${empty myPost && not empty day}">
                 <p id="info">해당날짜의 인증글이 없습니다.</p>
            </c:if>
      </div>
</div>
</BODY>
</HTML>