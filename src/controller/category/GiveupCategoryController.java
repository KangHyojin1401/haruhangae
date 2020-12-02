package controller.category;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.CategoryDTO;
import model.MissionDTO;
import model.UserDTO;
import model.dao.CategoryDAO;
import model.dao.DAOFactory;
import model.dao.MissionDAO;
import model.dao.UserDAO;
import model.service.GivenUpFailedException;

public class GiveupCategoryController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DAOFactory daoFactory = new DAOFactory();
		UserDAO userDAO = daoFactory.getUserDAO();
		CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
		MissionDAO missionDAO = daoFactory.getMissionDAO();

		try {
			String userID = (String) request.getSession().getAttribute("userID");
			UserDTO user = userDAO.findUser(userID);

			if (missionDAO.giveUpMission(userID) == 0) { // 실패시
				throw new GivenUpFailedException("미션 포기 실패");
			}

			request.setAttribute("isGiveup", true);
			List<CategoryDTO> categoryList = categoryDAO.getCategoryList();
			request.setAttribute("categoryList", categoryList);

			MissionDTO mission = missionDAO.getTodayMission(userID);
			request.setAttribute("mission", mission);
			request.setAttribute("categoryList", categoryList);

			request.setAttribute("user", user);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/createCategory.jsp";
	}

}