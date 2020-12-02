package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.UserDTO;
import model.dao.UserDAO;
import model.dao.impl.UserDAOImpl;
import model.service.UserDeleteFailException;
import model.service.UserNotFoundException;

public class DeleteUserController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// 로그인 여부 확인
		if (!UserSessionUtils.hasLogined(session)) {
			return "redirect:/user/login"; // login form 요청으로 redirect
		}

		String deleteID = (String) session.getAttribute("userID");
		UserDAO userDAO = new UserDAOImpl();

		try {
			UserDTO user = userDAO.findUser(deleteID); // 수정하려는 사용자 정보 검색
			if (user == null) {
				System.out.println("hello");
				throw new UserNotFoundException(deleteID + "는 존재하지 않는 아이디입니다.");
			}
			request.setAttribute("user", user);

			if (UserSessionUtils.isLoginUser(deleteID, session)) { // 로그인한 사용자가 삭제 대상인 경우 (자기 자신을 삭제)
				if (userDAO.removeUser(deleteID) == 0) // 사용자 정보 삭제
					throw new UserDeleteFailException();

				session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
				session.invalidate();

				return "redirect:/user/goodbye";

			} else {
				throw new IllegalStateException("타인의 정보는 수정할 수 없습니다.");
			}
		} catch (UserNotFoundException e) { // 존재하지 않는 아이디
			request.setAttribute("userDeleteFailed", true);
			request.setAttribute("userNotFound", true);
			request.setAttribute("exception", e);
			return "redirect:/user/login/form";
		} catch (IllegalStateException e) { // 로그인한 사용자 != 수정하려는 사용자
			request.setAttribute("userDeleteFailed", true);
			request.setAttribute("exception", e);
			return "redirect:/user/login/form";
		} catch (UserDeleteFailException e) {
			e.printStackTrace();
			request.setAttribute("userDeleteFailed", true);
			request.setAttribute("exception", e);
			return "/user/myPage.jsp";
		}

	}
}