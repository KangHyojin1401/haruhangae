package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.UserDTO;
import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.service.UserNotFoundException;
import model.service.UserUpdateFailException;

public class UpdateUserController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		// 로그인 여부 확인
		if (!UserSessionUtils.hasLogined(session)) {
			return "redirect:/user/login/form"; // login form 요청으로 redirect
		}

		String userID = (String) session.getAttribute("userID");

		DAOFactory daoFactory = new DAOFactory();
		UserDAO userDAO = daoFactory.getUserDAO();
		// GET방식 요청 -> form제공
		if (request.getMethod().equals("GET")) {
			try {
				UserDTO user = userDAO.findUser(userID); // 수정하려는 사용자 정보 검색
				if (user == null) {
					throw new UserNotFoundException(userID + "는 존재하지 않는 아이디입니다.");
				}

				request.setAttribute("user", user);

				if (UserSessionUtils.isLoginUser(userID, session)) { // 현재 로그인한 사용자가 수정 대상 사용자인 경우 -> 수정 가능
					return "/user/updateForm.jsp"; // 검색한 사용자 정보를 update form으로 전송
				} else {
					throw new IllegalStateException("타인의 정보는 수정할 수 없습니다.");
				}
			} catch (UserNotFoundException e) { // 존재하지 않는 아이디
				request.setAttribute("updateFailed", true);
				request.setAttribute("userNotFound", true);
				request.setAttribute("exception", e);
				return "redirect:/user/login/form"; // 그럼 request에 저장한 의미 x
			} catch (IllegalStateException e) { // 로그인한 사용자 != 수정하려는 사용자
				request.setAttribute("updateFailed", true);
				request.setAttribute("exception", e);
				return "redirect:/user/login/form"; // 그럼 request에 저장한 의미 x
			}
		}

		// POST방식 요청 -> 데이터 저장
		UserDTO updateUser = new UserDTO(userID, request.getParameter("password"), request.getParameter("alias"),
				request.getParameter("email"), request.getParameter("phone"));

		try {
			if (userDAO.updateUser(updateUser) == 0)
				throw new UserUpdateFailException();

			return "redirect:/user/myPage";
		} catch (UserUpdateFailException e) {
			request.setAttribute("userUpdateFailed", true);
			request.setAttribute("exception", e);

			return "/user/updateForm.jsp";
		}
	}
}
