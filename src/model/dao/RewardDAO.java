package model.dao;

import java.util.List;

import model.RewardDTO;

public interface RewardDAO {
	public List<RewardDTO> getRewardList(String userID);

	public int earningReward(String userID);
}
