package model.dao;

import java.util.List;

import model.CategoryDTO;

public interface CategoryDAO {
	public int createUserCategory(String userID, int catID);

	public int chooseCategory(String userID, int catID);

	public List<CategoryDTO> getCategoryList();

	public int categoryPassedDay(String userID);

	public int getMissionSuccess(String userID);

	public int hasUserCategory(String userID);

	public int giveUpCategory(String userID);

	public int isGiveupCategory(String userID);
}