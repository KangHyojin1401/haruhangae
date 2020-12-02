package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.UserDTO;
import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.service.ExistingUserException;
import model.service.RegisterFailException;

public class RegisterUserController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DAOFactory daoFactory = new DAOFactory();
		UserDAO userDAO = daoFactory.getUserDAO();

		// GET방식 요청 -> form제공
		if (request.getMethod().equals("GET")) {
			return "/user/registerForm.jsp";
		}

		// POST방식 요청 -> 데이터 저장
		UserDTO user = new UserDTO(request.getParameter("userID"), request.getParameter("password"),
				request.getParameter("alias"), request.getParameter("email"), request.getParameter("phone"));

		try {
			if (userDAO.existingUser(user.getUserID()) == true)
				throw new ExistingUserException(user.getUserID() + "는 존재하는 아이디입니다.");

			if (userDAO.createUser(user) == 0)
				throw new RegisterFailException("등록에 실패하였습니다.");

			return "redirect:/user/hello"; // 성공 시 홈으로 redirect

		} catch (Exception e) {
			request.setAttribute("registerFailed", true);
			request.setAttribute("exception", e);
			request.setAttribute("user", user);
			return "/user/registerForm.jsp"; // 예외 발생 시 회원가입 form으로 forwarding
		}
	}
}