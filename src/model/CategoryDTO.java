package model;

public class CategoryDTO {
	private int catID;
	private String catName;
	private String catContent;
	private int todayMissionID;

	public CategoryDTO() {
		super();
	}

	public CategoryDTO(int catID, String catName, String catContent, int todayMissionID) {
		super();
		this.catID = catID;
		this.catName = catName;
		this.catContent = catContent;
		this.todayMissionID = todayMissionID;
	}

	public CategoryDTO(int catID, int todayMissionID) {
		super();
		this.catID = catID;
		this.todayMissionID = todayMissionID;
	}

	public int getCatID() {
		return catID;
	}

	public void setCatID(int catID) {
		this.catID = catID;
	}

	public String getCatContent() {
		return catContent;
	}

	public void setCatContent(String catContent) {
		this.catContent = catContent;
	}

	public int getTodayMissionID() {
		return todayMissionID;
	}

	public void setTodayMissionID(int todayMissionID) {
		this.todayMissionID = todayMissionID;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

}
