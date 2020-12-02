package model;

public class UserDTO {
	private String userID;
	private String password;
	private String alias;
	private String email;
	private String phone;
	private int totalMission;
	private int totalReward;
	private String catName;

	public UserDTO() {
	};

	public UserDTO(String userID, String password, String alias, String email, String phone) {
		super();
		this.userID = userID;
		this.password = password;
		this.alias = alias;
		this.email = email;
		this.phone = phone;
	}

	public UserDTO(String userID, String password, String alias, String email, String phone, int totalMission,
			int totalReward, String catName) {
		super();
		this.userID = userID;
		this.password = password;
		this.alias = alias;
		this.email = email;
		this.phone = phone;
		this.totalMission = totalMission;
		this.totalReward = totalReward;
		this.catName = catName;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getTotalMission() {
		return totalMission;
	}

	public void setTotalMission(int totalMission) {
		this.totalMission = totalMission;
	}

	public int getTotalReward() {
		return totalReward;
	}

	public void setTotalReward(int totalReward) {
		this.totalReward = totalReward;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public boolean matchPassword(String password) {
		if (password == null) {
			return false;
		}
		return this.password.equals(password);
	}

	public boolean isSameUser(String userID) {
		return this.userID.equals(userID);
	}

}
