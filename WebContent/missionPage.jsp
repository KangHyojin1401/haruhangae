<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>미션 상세 페이지</title>

<script src="http://code.jquery.com/jquery-latest.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function togglePostFormDiv() {
		//$("#post_form_div").hide();                            // 숨기기. 영역도 같이 사라짐.
		$("#post_form_div").toggle(300); // hide(), show() 함수가 toggle 됨.
		//$("#hh").css('visibility', 'hidden'); // 숨기기. 영역은 같이 사라지지 않음.
		//$("#hh1").attr("disabled", "true"); // 숨기기는 아님. form 으로 전달되지 않음.
	}

	function toggleSearchFormDiv() {
		$("#search_form_div").toggle(300);
	}

	function confirmGiveUp() {
		if (confirm("미션을 포기하시면 카테고리도 함께 포기됩니다. 정말 포기하시겠습니까?")) {
			location.href = "/haruhangae/mission/giveUp";
		} else {
			alert("취소되었습니다.");
		}
	}
	
	function setTitle() {
		$("#page_name").text("MISSION & POST");
	}
</script>
<style type="text/css">
div {
	 /*border: 1px solid black;*/ 
}



input[type=text] {
	border: 0px;
	background-color: #fffefa;
}

.mission_wrap {
	padding: 5% 5% 5% 5%;
	border: 0px solid black;
	border-radius: 10px;
	background-color: #dedcee;
}

#arrow_div, #today_mission_div, #start_day_div {
	text-align: left;
}

#menu_div, #giveup_div, #finish_day_div {
	text-align: right;
}

#mission_div {
	text-align: center;
	font-size: 120%;
}

.container {
	width: 90%;
	margin: auto;
}

#today_mission_div, #start_day_div {
	display: inline-block;
	width: 49%;
}

#giveup_btn {
	padding: 5px 10px 5px 10px;
	font-size: small;
}

#giveup_div, #finish_day_div {
	display: inline-block;
	width: 49%;
}

#start_day_div, #finish_day_div {
	font-size: 80%;
}

#row1 {
	vertical-align: middle;
	overflow: hidden;
	align-items: center;
}

#mission_div {
	text-align: center;
	font-size: 120%;
	font-weight: bolder;
	color: #50487d;
}

img {
	width: 30px;
}

#reload_img, #logo_img, .post_img, #menu_img {
	width: 30px;
}

#search_img {
	width: 15px;
}

#search_form_div, #post_form_div {
	display: none;
}

button, #submit_btn {
	background-color: transparent;
	border-radius: 20px;
	padding: 3px 0px 3px 0px;
	background-color: #6a60a9;
	color: white;
	font-size: medium;
	font-size: 90%;
	border: 0px;
	box-shadow: none;
}

#submit_btn {
	padding: 5px 10px 5px 10px;
}

#search_img_btn {
	width: 10%;
	height: 10%;
	background-color: transparent;
}

#search_img {
	width: 40px;
}

#rating_prgs {
	width: 100%;
	
}

td {
	padding: 1% 0% 1% 0%;
}

/* progress[value] {
  -webkit-appearance: none;
  appearance: none;
}

progress[value]::-webkit-progress-bar {
	border-color:  #fbd14b;
	color-adjust: #fbd14b;
	border-color: #fbd14b;
	color: #fbd14b;
	text-decoration-color: #fbd14b;
	lighting-color: #fbd14b;
	scrollbar-color: #fbd14b;
	scrollbar-base-color: #fbd14b;
  background-color: #fbd14b;
} */
/* table, tr, td {
	border: 1px solid black;
} */


table {
	width: 100%;
}

#rating_td {
	width: 50%;
}

#submit_div {
	padding: 20px 0px 20px 0px;
	text-align: center;
}

#post_form_div, #search_form_div {
	padding: 5% 0% 0% 5%;
	border: 0px solid black;
	border-radius: 10px;
	background-color: #dedcee;
}

#search_form_div {
	padding: 5% 5% 5% 5%;
	font-size: 85%;
}

#myPost_div, #postlist_div {
	padding: 5% 5% 5% 5%;
	border: 3px solid  #6a60a9;
	border-radius: 10px;
}

#postlist_div {
	border-color:#dedcee;
}

.post_contents {
	padding: 3% 0% 3% 0%;
	font-size: 110%;
}

.post_tag, .post_rating {
	font-size: 90%;
}

#rating_div, .s_f_wrap {
	display: inline-block;
	width: 86%
}

#count_day_div{
	display: inline-block;
	font-size: 10%;
	text-align: center;
	width: 11%
}

#post_btn_div {
	display: inline-block;
	text-align: center;
	width: 49%
}

#post_btn, #search_btn {
	width: 80%;
	padding: 5px 0px 5px 0px;
}

#search_btn_div {
	display: inline-block;
	text-align: center;
	width: 49%
}

#search_content {
	height: 30px;
} 

#reload_div {
	text-align: right;
}

.date {
	font-size: 50%;
	color: gray;
}

.rating {
	color: #fbd14b;
	font-weight: bold;
}

.post_location {
	font-size: 70%;
	color: gray;
}

p {
	text-align: center;
}

#no_today_post {
	font-size: 90%;
	color: gray;
}
</style>
</head>
<body onload="setTitle(); ">
	<div class="container">
		<%@ include file="/topBar.jsp"%>			
		<br> <br> <br> <br> 

		<div class="mission_wrap">
			<div id="row1">
				<div id="today_mission_div">오늘의 미션</div>
				<div id="giveup_div">
					<c:if test="${ myPost == null }">
						<button id="giveup_btn" onclick="confirmGiveUp()">미션 포기</button>
					</c:if>
				</div>
			</div>
			<div id="mission_div"><p>${mission.missionContent}</p></div>
			<div id="rating_div">
				<progress id="rating_prgs" value="${missionClearCnt}" max="14"></progress>
			</div>
			<div id="count_day_div">${missionClearCnt}일달성</div>
			<div class="s_f_wrap">
				<div id="start_day_div">0일</div>
				<div id="finish_day_div">14일</div>
			</div>
		</div>

		<br>

		<div class="post_btn_wrap">
			<div id="post_btn_div">
				<c:if test="${ myPost == null }">
					<button id="post_btn" onclick="togglePostFormDiv()">인증하기</button>	
				</c:if>
				<c:if test="${ myPost != null }">
					<button id="post_btn" onclick="togglePostFormDiv()">수정하기</button>	
				</c:if>
			</div>
			<div id="search_btn_div">
				<button id="search_btn" onclick="toggleSearchFormDiv()">검색하기</button>
			</div>
		</div>

		<br>

		<div id="post_form_div">
			<form method="post" action="<c:url value='/post/register' />">
				<c:if test="${ myPost == null }">
					<table id="post_form_table">
						<tr>
							<td class="td1" id="rating_td">만족도</td>
							<td><input name="rating" type="text" maxlength="1" size="1" required />점</td>
						</tr>
						<tr>
							<td>한 줄 후기</td> 
							<td><input name="content" type="text" required /></td>
						</tr>
						<tr>
							<td>위치</td> 
							<td><input name="location" type="text" /></td>
						</tr>
						<tr>
							<td>태그</td> 
							<td><input name="tags" type="text" value="태그 앞에  # 을 붙여주세요." onfocus="this.value=''"/></td>
						</tr>
						<tr>
							<td>인증 사진</td> 
							<td><input name="photo" type="file" /></td>
						</tr>
						<tr>
							<td>게시글 공개 여부</td>
							<td>	
								<label for="public">공개 <input name="is_private" type="radio" id="public" value="0" required> </label>
								<label for="private">비공개<input name="is_private" type="radio" id="private" value="1" required ></label> 
							</td>
						</tr>
					</table>
					<div id="submit_div">
						<input type="submit" value="게시글 작성" id="submit_btn" />
					</div>
				</c:if>
			</form>
			
			<form method="post" action="<c:url value='/post/update' />">
				<c:if test="${ myPost != null }">
					<table id="post_form_table">
						<tr>
							<td class="td1" id="rating_td">만족도</td>
							<td><input name="rating" value="${ myPost.rating }" type="text" maxlength="1" size="1" required />점</td>
						</tr>
						<tr>
							<td>한 줄 후기</td> 
							<td><input name="content" value="${ myPost.content }" type="text" required /></td>
						</tr>
						<tr>
							<td>위치</td> 
							<td><input name="location" type="text" value="${ myPost.location }" /></td>
						</tr>
						<tr>
							<td>태그</td> 
							<c:forEach var="tag" items="${ myPost.tags }" varStatus="status">
								<c:set var="tagString" value= "${ tagString }${ status.first ? '#' : ' #' }${ tag }" scope="request"></c:set>
							</c:forEach>
							<td><input name="tags" type="text" value="${ tagString }"/></td>
						</tr>
						<tr>
							<td>인증 사진</td> 
							<td><input name="photo" type="file" value="${ myPost.photoAddr }"/></td>
						</tr>
						<tr>
							<td>게시글 공개 여부</td>
							<td>	
								<c:if test="${ myPost.isPrivate != 1 }">
									<label for="public">공개 <input name="is_private" type="radio" id="public" value="0" checked required> </label>
									<label for="private">비공개<input name="is_private" type="radio" id="private" value="1" required ></label> 
								</c:if>
								<c:if test="${ myPost.isPrivate == 1 }">
									<label for="public">공개 <input name="is_private" type="radio" id="public" value="0" required> </label>
									<label for="private">비공개<input name="is_private" type="radio" id="private" value="1" checked required ></label> 
								</c:if>
							</td>
						</tr>
					</table>
					<div id="submit_div">
						<input type="submit" value="게시글 수정" id="submit_btn" />
					</div>
				</c:if>
			</form>
		</div>

		<br>

		<div id="search_form_div">
			<c:url value='/post/search' var="url">
				<c:param name="missionID" value="${ mission.missionID }" />
				<c:param name="catID" value="${ mission.catID }" />
				<c:param name="content" value="${ mission.missionContent }" />
				<c:param name="missionClearCnt" value="${ missionClearCnt }"/>
			</c:url>

			<form method="post" action="${ url }">
				<table>
					<tr>
						<td>태그 검색</td> 
						<td><input id="search_tag" name="tag" type="text" size="30" value="# 을 뺀 하나의 태그만 입력해주세요." onfocus="this.value=''"/></td>
						<td rowspan="2">	
							<button id="search_img_btn" type="submit">
								<img id="search_img" src="<c:url value='/images/search_icon.png'/>" alt="검색">
							</button>
						</td>
					</tr>
					<tr>
						<td>한 줄 후기<br>내용 중 검색</td>
						<td><input id="search_content" size="30" name="contentKeyword" type="text" /></td>
					</tr>
				</table>
			</form>
		</div>

		<br>

		<div class="posts_wrap">
			<div id="reload_div">
				<img id="reload_img" src="<c:url value='/images/post_refresh.png'/>" onclick="location.href='/haruhangae/mission/print'" alt="새로고침">
			</div>

			<div class="print_post" id="myPost_div">
				<c:if test="${ myPost != null }">
					<!-- <img id="my_post_img" src="<c:url value='/images/logo_square.png'/>"
						class="post_img" alt="default image(로고)"> -->
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
				
				<c:if test="${ myPost == null }">
                  	<span id="no_today_post">오늘 인증글을 올리지 않았어요.</span>
            	</c:if>
			</div>
			
			<br>
			<br>
			<br>
			
			
			<c:if test="${ myPost == null && noPostList == true }">
                  	<p>오늘 올라온 포스트가 없어요. <br>오늘의 첫 포스트를 작성해보세요!</p>
            </c:if>
			<c:if test="${ postList == null && keywordNotFound == true && noPostList == null }">
                  	<p>검색어를 입력해주세요.</p>
            </c:if>
			<c:if test="${ postNotFound == true && noPostList == null  }">
                  	<p>검색된 결과가 없습니다.</p>
            </c:if>

			<c:forEach var="post" items="${ postList }">
				<div class="print_post" id="postlist_div">
					<!-- <img id="post_list_img"
						src="<c:url value='/images/logo_square.png'/>" class="post_img"
						alt="default image(로고)"> -->
					<div class="post_rating">만족도: <span class="rating">${ post.rating }</span>점</div>
					<div class="post_location">${ post.location }</div>
					<div class="post_contents">${ post.content }</div>
					<div class="post_tag">
						<c:forEach var="tag" items="${ post.tags }">
							<c:if test="${ tag != null }">
								<span>#${ tag }&nbsp;</span>
							</c:if>
						</c:forEach>
					</div>
					<div class="date">${ post.date } 작성</div>
				</div>
				<br>
			</c:forEach>
		</div>

	</div>
</body>
</html>