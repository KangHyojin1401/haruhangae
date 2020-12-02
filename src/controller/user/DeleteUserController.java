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

		// �α��� ���� Ȯ��
		if (!UserSessionUtils.hasLogined(session)) {
			return "redirect:/user/login"; // login form ��û���� redirect
		}

		String deleteID = (String) session.getAttribute("userID");
		UserDAO userDAO = new UserDAOImpl();

		try {
			UserDTO user = userDAO.findUser(deleteID); // �����Ϸ��� ����� ���� �˻�
			if (user == null) {
				System.out.println("hello");
				throw new UserNotFoundException(deleteID + "�� �������� �ʴ� ���̵��Դϴ�.");
			}
			request.setAttribute("user", user);

			if (UserSessionUtils.isLoginUser(deleteID, session)) { // �α����� ����ڰ� ���� ����� ��� (�ڱ� �ڽ��� ����)
				if (userDAO.removeUser(deleteID) == 0) // ����� ���� ����
					throw new UserDeleteFailException();

				session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
				session.invalidate();

				return "redirect:/user/goodbye";

			} else {
				throw new IllegalStateException("Ÿ���� ������ ������ �� �����ϴ�.");
			}
		} catch (UserNotFoundException e) { // �������� �ʴ� ���̵�
			request.setAttribute("userDeleteFailed", true);
			request.setAttribute("userNotFound", true);
			request.setAttribute("exception", e);
			return "redirect:/user/login/form";
		} catch (IllegalStateException e) { // �α����� ����� != �����Ϸ��� �����
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