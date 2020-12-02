package model;

public class MissionDTO {
	private String missionContent;
	private int catID;
	private int missionID;
	private String catName;

	public MissionDTO() {
	}

	public MissionDTO(String missionContent, int catID, int missionID, String catName) {
		super();
		this.missionContent = missionContent;
		this.catID = catID;
		this.missionID = missionID;
		this.catName = catName;
	}

	public MissionDTO(String missionContent, int catID, int missionID) {
		super();
		this.missionContent = missionContent;
		this.catID = catID;
		this.missionID = missionID;
	}

	public MissionDTO(int catID, int missionID) {
		super();
		this.catID = catID;
		this.missionID = missionID;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String string) {
		this.catName = string;
	}

	public String getMissionContent() {
		return missionContent;
	}

	public void setMissionContent(String missionContent) {
		this.missionContent = missionContent;
	}

	public int getCatID() {
		return catID;
	}

	public void setCatID(int catID) {
		this.catID = catID;
	}

	public int getMissionID() {
		return missionID;
	}

	public void setMissionID(int missionID) {
		this.missionID = missionID;
	}
}
