package controller.category;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.MissionDTO;
import model.UserDTO;
import model.dao.CategoryDAO;
import model.dao.DAOFactory;
import model.dao.MissionDAO;
import model.dao.UserDAO;

public class CreateCategoryController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DAOFactory daoFactory = new DAOFactory();
		UserDAO userDAO = daoFactory.getUserDAO();
		CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
		MissionDAO missionDAO = daoFactory.getMissionDAO();

		String userID = (String) request.getSession().getAttribute("userID");

		try {
			UserDTO user = userDAO.findUser(userID);

			String catID = request.getParameter("category");
			categoryDAO.createUserCategory(userID, Integer.parseInt(catID));

			MissionDTO mission = missionDAO.getTodayMission(userID);

			request.setAttribute("mission", mission);
			request.setAttribute("user", user);

		} catch (Exception e) {
			e.printStackTrace();
			return "/home.jsp";

		}
		return "/home.jsp";
	}

}