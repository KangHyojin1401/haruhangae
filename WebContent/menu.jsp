<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.Calendar"%>

<%
   Calendar mtoday = Calendar.getInstance();
   int mYear = mtoday.get(Calendar.YEAR);
   int mMonth = mtoday.get(Calendar.MONTH);
   int mDay = mtoday.get(Calendar.DATE);
   %>
<!DOCTYPE html>
<html>
<head>
<style>
.menuwrap {
   position: fixed;
   right: -300px; /* 너비 300px 인 사이드바를 오른쪽으로 300px 이동시켜 화면에 보이지 않게 함 */
   z-index: 100; /* 배치 순서 상위 */
   overflow: auto; /* 컨텐츠 량에 따라 스크롤바를 추가할지 자동으로 결정 */
   top: 0px;
   width: 300px; /* 너비 */
   height: 100%;
   padding: 50px 20px;
   box-sizing: border-box;
   background-color: #f4f2f5;
}

.menuwrap.on {
   right: 0px;
}
</style>
<script type="text/javascript">
function slideMenu() {
   //$('.menuwrap').css('right', '0px');
    
   if (document.querySelector('.menuwrap').classList.contains('on') ){
        //메뉴닫힘
        document.querySelector('.menuwrap').classList.remove('on');
    } else {
        //메뉴펼처짐
        document.querySelector('.menuwrap').classList.add('on');
    }
}
</script>
<meta charset="UTF-8">
<title>Menu</title>
<style>
ul {
   list-style: none;
   text-indent: 3px;
}

ul li {
   line-height: 28px;
   border-bottom: 1px solid #333;
}

ul li a {
   color: black;
   display: block;
   text-decoration: none
}

ul li a:hover {
   background: #cccccc;
}

A:link {
   color: #000000;
   text-decoration: none;
}

A:visited {
   color: #000000;
   text-decoration: none;
}

A:active {
   color:  #6a60a9;
   text-decoration: none;
}

A:hover {
   color:  #6a60a9;
   text-decoration: none;
}
</style>
</head>
<body>
   <div style="text-align: right">
      <a href="javascript:void(0)" onClick="slideMenu()"><img id="menu_img" src="<c:url value='/images/menu_icon.png'/>" alt="메뉴"></a>
   </div>
   <div class="menuwrap">
      <a href="javascript:void(0)" onClick="slideMenu()">close</a>
      <nav id="menu">
         <ul class="menu_list">
            <li><a href="<c:url value='/home' />">HOME</a></li>
            <li><a href="<c:url value='/mission/print' />">MISSION</a></li>
            <c:url value="/calendar/list" var="url">
               <c:param name="userID" value="${userID}" />
               <c:param name="year" value="<%= Integer.toString(mYear) %>" />
               <c:param name="month" value="<%= Integer.toString(mMonth) %>" />
               <c:param name="day" value="<%= Integer.toString(mDay) %>" />
            </c:url>
            <li><a href="${url}">CALENDAR</a></li>
            <li><a href="<c:url value='/reward/list' />">REWARD</a></li>
            <li><a href="<c:url value='/user/myPage' />">MY PAGE</a></li>
            <li><a href="<c:url value='/user/logout' />">LOGOUT</a></li>
         </ul>
      </nav>
   </div>
</body>
</html>