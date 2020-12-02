package controller.mission;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.MissionDTO;
import model.PostDTO;
import model.UserDTO;
import model.dao.CategoryDAO;
import model.dao.DAOFactory;
import model.dao.MissionDAO;
import model.dao.PostDAO;
import model.dao.UserDAO;

public class ViewMissionController implements Controller { // home -> mission 상세 페이지 이동
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userID = (String) request.getSession().getAttribute("userID");

		DAOFactory daoFactory = new DAOFactory();

		UserDAO userDAO = daoFactory.getUserDAO();
		MissionDAO missionDAO = daoFactory.getMissionDAO();
		PostDAO postDAO = daoFactory.getPostDAO();
		CategoryDAO categoryDAO = daoFactory.getCategoryDAO();

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar time = Calendar.getInstance();
		String date = format.format(time.getTime());

		try {
			UserDTO user = userDAO.findUser(userID);
			MissionDTO mission = missionDAO.getTodayMission(userID);

			PostDTO myPost = postDAO.findMyPost(userID, date);
			List<PostDTO> postList = postDAO.findPostList(userID, date);
			int missionClearCnt = categoryDAO.getMissionSuccess(userID);

			request.setAttribute("missionClearCnt", missionClearCnt);
			request.setAttribute("mission", mission);
			request.setAttribute("user", user);
			request.setAttribute("myPost", myPost);
			request.setAttribute("postList", postList);
			
			if (postList.size() == 0) {
				request.setAttribute("noPostList", true);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/missionPage.jsp";
	}
}