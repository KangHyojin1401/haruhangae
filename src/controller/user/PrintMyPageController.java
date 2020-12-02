package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.UserDTO;
import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.service.UserNotFoundException;

public class PrintMyPageController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userID = (String) request.getSession().getAttribute("userID");

		DAOFactory daoFactory = new DAOFactory();
		UserDAO userDAO = daoFactory.getUserDAO();

		try {
			UserDTO user = userDAO.findUser(userID);
			if (user == null) {
				throw new UserNotFoundException(userID + "는 존재하지 않는 아이디입니다.");
			}
			request.setAttribute("user", user);

			return "/user/myPage.jsp";
		} catch (UserNotFoundException e) { // 실패시
			request.setAttribute("userNotFound", true);
			request.setAttribute("exception", e);
			return "/home.jsp";
		}
	}

}
