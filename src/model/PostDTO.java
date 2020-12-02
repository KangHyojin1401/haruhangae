package model;

import java.util.ArrayList;

public class PostDTO {
	int postID;
	String location;
	int rating;
	String date;
	String content;
	int isPrivate;
	String photoAddr;
	String userID;
	String alias;
	String missionContent;
	ArrayList<String> tags;

	public PostDTO() {

	}

	public PostDTO(String alias, String date) {
		this.alias = alias;
		this.date = date;
	}

	public PostDTO(int postID, String location, int rating, String content, int isPrivate, String photoAddr) {
		super();
		this.postID = postID;
		this.location = location;
		this.rating = rating;
		this.content = content;
		this.isPrivate = isPrivate;
		this.photoAddr = photoAddr;
	}

	public PostDTO(String location, int rating, String date, String content, int isPrivate, String photoAddr,
			String userID, String alias, String missionContent) {
		this.location = location;
		this.rating = rating;
		this.date = date;
		this.content = content;
		this.isPrivate = isPrivate;
		this.photoAddr = photoAddr;
		this.userID = userID;
		this.alias = alias;
		this.missionContent = missionContent;
	}

	public PostDTO(int postID, String location, int rating, String date, String content, int isPrivate,
			String photoAddr, String userID, String alias, String missionContent, ArrayList<String> tags) {
		this.postID = postID;
		this.location = location;
		this.rating = rating;
		this.date = date;
		this.content = content;
		this.isPrivate = isPrivate;
		this.photoAddr = photoAddr;
		this.userID = userID;
		this.alias = alias;
		this.missionContent = missionContent;
		this.tags = tags;
	}

	public int getPostID() {
		return postID;
	}

	public void setPostID(int postID) {
		this.postID = postID;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(int isPrivate) {
		this.isPrivate = isPrivate;
	}

	public String getPhotoAddr() {
		return photoAddr;
	}

	public void setPhotoAddr(String photoAddr) {
		this.photoAddr = photoAddr;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getMissionContent() {
		return missionContent;
	}

	public void setMissionContent(String missionContent) {
		this.missionContent = missionContent;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

}
