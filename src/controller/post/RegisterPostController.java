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
import model.service.CreatingFailedException;

public class RegisterPostController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		DAOFactory factory = new DAOFactory();
		PostDAO postDAO = factory.getPostDAO();

		String userID = (String) request.getSession().getAttribute("userID");

		// POST: 게시물 등록 처리
		int rating = -1;
		int isPrivate = -1;
		
		String location = request.getParameter("location");
		String sRating = request.getParameter("rating");
		if (sRating != null)
			rating = Integer.parseInt(sRating);
		String content = request.getParameter("content");
		String sIsPrivate = request.getParameter("is_private");
		if (sIsPrivate != null)
			isPrivate = Integer.parseInt(sIsPrivate);
		String photoAddr = request.getParameter("photo");
		String tags = request.getParameter("tags");

		try {
			// 폼에서 필요한 요소들을 다 채우지 않은 경우 예외 처리
			if (rating == 0) {
				request.setAttribute("RatingNotInputException", true);
			} else if (content == null && photoAddr == null) {
				request.setAttribute("ContentNotInputException", true);
			}

			PostDTO post = new PostDTO(location, rating, null, content, isPrivate, photoAddr, userID, null, null);

			int result = postDAO.createPost(post); // Post 등록
			if (result == 0) {
				throw new CreatingFailedException("게시물 등록 실패");
			}

			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Calendar time = Calendar.getInstance();
			String date = format.format(time.getTime());
			
			post = postDAO.findMyPost(userID, date);
			
			Pattern p = Pattern.compile("\\#([0-9a-zA-Zㄱ-ㅎ가-힣ㅏ-ㅣ]*)");
			Matcher m = p.matcher(tags);
			while (m.find()) {
				String tagName = m.group().substring(1);
				postDAO.insertTag(tagName); // Tag 등록
				postDAO.insertPostTag(post.getPostID(), tagName); // PostTag 등록
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/mission/print";
	}

}