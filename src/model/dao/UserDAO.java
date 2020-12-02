package model.dao;

import model.UserDTO;

public interface UserDAO {
	public int createUser(UserDTO user);

	public int updateUser(UserDTO user);

	public int removeUser(String userID);

	public UserDTO findUser(String userID);

	public int addTotalMission(String userID);

	public int addTotalReward(String userID);

	public String findCatName(String userID);

	public boolean isSuccessed(String userID, String catID);

	public boolean existingUser(String userID);
}
