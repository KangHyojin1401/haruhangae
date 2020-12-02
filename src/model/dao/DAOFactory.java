package model.dao;

import model.dao.impl.CategoryDAOImpl;
import model.dao.impl.MissionDAOImpl;
import model.dao.impl.PostDAOImpl;
import model.dao.impl.RewardDAOImpl;
import model.dao.impl.UserDAOImpl;

// DAO 를 구현한 Implementation 객체를 생성하는 클래스
public class DAOFactory {

	public MissionDAO getMissionDAO() {
		return new MissionDAOImpl();
	}

	public PostDAO getPostDAO() {
		return new PostDAOImpl();
	}

	public RewardDAO getRewardDAO() {
		return new RewardDAOImpl();
	}

	public UserDAO getUserDAO() {
		return new UserDAOImpl();
	}

	public CategoryDAO getCategoryDAO() {
		return new CategoryDAOImpl();
	}

}