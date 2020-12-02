<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
.topbar_table {
	display: table;
	width: 100%;
	margin: auto;
	text-align: center;
}

.topbar_table {
	table-layout: fixed;
	background-color: rgba( 255, 255, 255, 0.5 );
}

.topbar_tr {
	/*position: fixed; 메뉴 상단바 고정 */
	display: table-row;
}

#fixed {
	position: fixed;
	width: 87%;
}

.topbar_td {
	display: table-cell;
	vertical-align: middle;
}

#page_name {
	font-weight: bolder;
}

img {
	width: 30px;
}

#reload_img, #logo_img, .post_img, #menu_img {
	width: 30px;
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
	color: red;
	text-decoration: none;
}

A:hover {
	color: red;
	text-decoration: none;
}
</style>
</head>
<body>
	<div id="fixed">
		<div class="topbar_table">
			<div class="topbar_tr" id="topbar_div">
				<div class="topbar_td" id="arrow_div">
					<div>
						<a href="<c:url value='/home'/>"><img id="home_img"
							src="<c:url value='/images/home_icon.png'/>" alt="메인페이지 이동"></a>
					</div>
				</div>
				<div class="topbar_td" id="title_div">
					<div>
						<span id="page_name"></span>
					</div>
				</div>
				<div class="topbar_td" id="menu_div">
					<%@ include file="/menu.jsp"%>
				</div>
			</div>
		</div>
		</div>
</body>
</html>