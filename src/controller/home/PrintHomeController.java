package controller.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.MissionDTO;
import model.UserDTO;
import model.dao.DAOFactory;
import model.dao.MissionDAO;
import model.dao.UserDAO;

public class PrintHomeController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userID = (String) request.getSession().getAttribute("userID");

		DAOFactory factory = new DAOFactory();
		MissionDAO missionDAO = factory.getMissionDAO();
		UserDAO userDAO = factory.getUserDAO();

		try {
			UserDTO user = userDAO.findUser(userID);
			MissionDTO mission = missionDAO.getTodayMission(userID);

			request.setAttribute("user", user);
			request.setAttribute("mission", mission);

			return "/home.jsp";
		} catch (Exception e) {
			e.toString();
		}
		return "/home.jsp";
	}

}