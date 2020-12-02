package controller.post;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.PostDTO;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.service.UpdatingFailedException;

public class UpdatePostController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		DAOFactory factory = new DAOFactory();
		PostDAO postDAO = factory.getPostDAO();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar time = Calendar.getInstance();
		String date = format.format(time.getTime());

		String userID = (String) request.getSession().getAttribute("userID");
		PostDTO myPost = postDAO.findMyPost(userID, date);

		
		// POST: 게시물 등록 처리
		int rating = -1;
		int isPrivate = -1;
		
		String location = request.getParameter("location");
		String sRating = request.getParameter("rating");
		rating = Integer.parseInt(sRating);
		String content = request.getParameter("content");
		String sIsPrivate = request.getParameter("is_private");
		isPrivate = Integer.parseInt(sIsPrivate);
		String photoAddr = request.getParameter("photoAddr");
		String tags = request.getParameter("tags");

		try {
			// 폼에서 필요한 요소들을 다 채우지 않은 경우 예외 처리
			if (rating == 0) {
				request.setAttribute("RatingNotInputException", true);
			} else if (content == null && photoAddr == null) {
				request.setAttribute("ContentNotInputException", true);
			}

			PostDTO post = new PostDTO(myPost.getPostID(), location, rating, content, isPrivate, photoAddr);

			int result = postDAO.updatePost(post); //Post 수정
			if (result == 0) {
				throw new UpdatingFailedException("게시물 수정 실패");
			}
			
			postDAO.deletePostTag(myPost.getPostID()); // PostTag 삭제
			
			Pattern p = Pattern.compile("\\#([0-9a-zA-Zㄱ-ㅎ가-힣ㅏ-ㅣ]*)");
			Matcher m = p.matcher(tags);
			while (m.find()) {
				String tagName = m.group().substring(1);
				postDAO.insertTag(tagName); // Tag 등록
				postDAO.updatePostTag(myPost.getPostID(), tagName); // PostTag 수정
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/mission/print";
	}

}
