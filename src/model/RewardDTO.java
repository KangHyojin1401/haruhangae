package model;

public class RewardDTO {
	private String name;
	private String contents;
	private String condition;
	private String iconAddr;
	private String userID;
	private String achievement;

	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String n) {
		achievement = n;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public String getContent() {
		return contents;
	}

	public void setContent(String c) {
		contents = c;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String c) {
		condition = c;
	}

	public String getIconAddr() {
		return iconAddr;
	}

	public void setIconAddr(String i) {
		iconAddr = i;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String u) {
		userID = u;
	}
}