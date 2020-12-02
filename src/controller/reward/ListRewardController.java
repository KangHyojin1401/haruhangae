package controller.reward;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.RewardDTO;
import model.UserDTO;
import model.dao.DAOFactory;
import model.dao.RewardDAO;
import model.dao.UserDAO;
import model.service.RewardNotFoundException;

//reward 페이지 접속 -> 획득한 reward 출력
public class ListRewardController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userID = (String) request.getSession().getAttribute("userID");

		DAOFactory daoFactory = new DAOFactory();
		RewardDAO rewardDAO = daoFactory.getRewardDAO();
		UserDAO userDAO = daoFactory.getUserDAO();

		try {

			// 획득한 리워드가 있다면 리워드 추가
			rewardDAO.earningReward(userID);

			UserDTO user = userDAO.findUser(userID);
			List<RewardDTO> rewardList = rewardDAO.getRewardList(userID);

			if (rewardList == null) {
				throw new RewardNotFoundException("아직 획득한 리워드가 없어요");
			}

			request.setAttribute("rewardList", rewardList);
			request.setAttribute("user", user);
			return "/reward.jsp";

		} catch (Exception e) {
			request.setAttribute("rewardNotFound", true);
			request.setAttribute("exception", e);
			return "/home.jsp";
		}
	}
}