package controller.post;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.MissionDTO;
import model.PostDTO;
import model.UserDTO;
import model.dao.DAOFactory;
import model.dao.MissionDAO;
import model.dao.PostDAO;
import model.dao.UserDAO;
import model.service.KeywordNotExistException;
import model.service.PostListNotFoundException;

public class SearchPostController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userID = (String) request.getSession().getAttribute("userID");

		String contentKeyword = request.getParameter("contentKeyword");
		String tag = request.getParameter("tag");
		
		if (tag.equals("# 을 뺀 하나의 태그만 입력해주세요.")) {
			tag = "";
		}
		
		int missionClearCnt = Integer.parseInt(request.getParameter("missionClearCnt"));

		DAOFactory factory = new DAOFactory();
		PostDAO postDAO = factory.getPostDAO();
		UserDAO userDAO = factory.getUserDAO();
		MissionDAO missionDAO = factory.getMissionDAO();

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar time = Calendar.getInstance();
		String date = format.format(time.getTime());

		MissionDTO mission = missionDAO.getTodayMission(userID);
		PostDTO myPost = postDAO.findMyPost(userID, date);

		try {
			List<PostDTO> postList = null;


			if (!contentKeyword.equals("") && tag.equals("")) { // 입력값이 키워드o, 태그x 일 때
				postList = postDAO.SearchPostListbyContents(mission, contentKeyword);
			}

			if (!tag.equals("") && contentKeyword.equals("")) { // 입력값이 키워드x, 태그o 일 때
				postList = postDAO.SearchPostListbyTag(mission, tag);
			}

			if (!tag.equals("") && !contentKeyword.equals("")) { // 입력값이 키워드o, 태그o 일 때
				postList = postDAO.SearchPostListbyContentsAndTag(mission, contentKeyword, tag);
			}

			UserDTO user = userDAO.findUser(userID);
			request.setAttribute("user", user);
			request.setAttribute("mission", mission);
			request.setAttribute("myPost", myPost);
			request.setAttribute("postList", postList);
			request.setAttribute("missionClearCnt", missionClearCnt);

			if (postList == null) { // 입력값이 키워드x, 태그x 일 때
				throw new KeywordNotExistException("검색어를 입력해주세요.");
			}

			if (postList.size() == 0) { // 태그나 키워드(검색어)를 넣어서 검색을 시도했지만 결과가 없을 때
				throw new PostListNotFoundException("검색된 결과가 없습니다.");
			}

			//request.setAttribute("keywordNotFound", false);
			request.setAttribute("postNotFound", false);
		} catch (PostListNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("postNotFound", true);
		} catch (KeywordNotExistException e) {
			request.setAttribute("keywordNotFound", true);
		}

		return "/missionPage.jsp";
	}

}