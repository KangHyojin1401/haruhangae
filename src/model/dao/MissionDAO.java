package model.dao;

import java.util.List;

import model.CategoryDTO;
import model.MissionDTO;

public interface MissionDAO {
	public int countMission(int catID);

	public int setTodayMission(int catID);

	public MissionDTO getTodayMission(String userID);

	public int giveUpMission(String userID);

	public List<CategoryDTO> getCategoryList();

}
