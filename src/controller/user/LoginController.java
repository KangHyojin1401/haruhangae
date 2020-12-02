package controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.CategoryDTO;
import model.MissionDTO;
import model.UserDTO;
import model.dao.CategoryDAO;
import model.dao.DAOFactory;
import model.dao.MissionDAO;
import model.dao.RewardDAO;
import model.dao.UserDAO;
import model.service.ChooseCategoryException;
import model.service.GiveupMissionFailException;
import model.service.PassedDayException;
import model.service.PasswordMismatchException;
import model.service.UserNotFoundException;

public class LoginController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DAOFactory daoFactory = new DAOFactory();
		UserDAO userDAO = daoFactory.getUserDAO();
		MissionDAO missionDAO = daoFactory.getMissionDAO();
		CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
		RewardDAO rewardDAO = daoFactory.getRewardDAO();

		// GET방식 요청 -> form제공
		if (request.getMethod().equals("GET")) {
			return "/user/login.jsp";
		}

		// POST방식 요청 -> 데이터 저장
		String userID = request.getParameter("userID");
		String password = request.getParameter("password");

		try {
			UserDTO user = userDAO.findUser(userID);

			if (user == null) {
				throw new UserNotFoundException(userID + "는 존재하지 않는 아이디입니다.");
			}

			if (!user.matchPassword(password)) {
				throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
			}

			// 세션에 사용자 아이디 저장
			HttpSession session = request.getSession();
			session.setAttribute(UserSessionUtils.USER_SESSION_KEY, userID);

			request.setAttribute("user", user);

			// 최초 1회
			if (categoryDAO.hasUserCategory(userID) == 0) {
				List<CategoryDTO> categoryList = categoryDAO.getCategoryList();

				request.setAttribute("hasCategory", false);
				request.setAttribute("categoryList", categoryList);

				throw new ChooseCategoryException("카테고리 선택으로 이동");
			}

			// 카테고리를 선택한지 14일이 경과돼 카테고리 재설정
			if (categoryDAO.categoryPassedDay(userID) > 14) {
				List<CategoryDTO> categoryList = categoryDAO.getCategoryList();
				request.setAttribute("passedDay", true);
				request.setAttribute("categoryList", categoryList);

				throw new PassedDayException("카테고리를 선택한 지 14일 초과, 카테고리 선택으로 이동");
			}

			// 카테고리를 포기한 상태라면
			if (categoryDAO.isGiveupCategory(userID) == 1) {
				request.setAttribute("isGiveup", true);
				List<CategoryDTO> categoryList = categoryDAO.getCategoryList();
				request.setAttribute("categoryList", categoryList);

				throw new GiveupMissionFailException("카테고리를 선택한 지 14일 초과, 카테고리 선택으로 이동");
			}

			missionDAO.setTodayMission(1);
			missionDAO.setTodayMission(2);
			missionDAO.setTodayMission(3);
			missionDAO.setTodayMission(4);

			MissionDTO mission = missionDAO.getTodayMission(userID);
			request.setAttribute("mission", mission);
			// 리워드 성공한게 있다면 리워드 추가
			rewardDAO.earningReward(userID);

			return "/home.jsp";
		} catch (UserNotFoundException e) {
			request.setAttribute("loginFailed", true);
			request.setAttribute("userNotFound", true);
			request.setAttribute("exception", e);
			return "/user/login.jsp";
		} catch (PasswordMismatchException e) {
			/*
			 * UserNotFoundException이나 PasswordMismatchException 발생 시 다시 login form을 사용자에게
			 * 전송하고 오류 메세지도 출력
			 */
			request.setAttribute("loginFailed", true);
			request.setAttribute("passwordMismatch", true);
			request.setAttribute("exception", e);

			return "/user/login.jsp";
		} catch (ChooseCategoryException e) {
			request.setAttribute("exception", e);
			return "/createCategory.jsp";
		} catch (PassedDayException e) {
			request.setAttribute("exception", e);
			return "/createCategory.jsp";
		} catch (GiveupMissionFailException e) {
			request.setAttribute("exception", e);
			return "/createCategory.jsp";
		}
	}
}