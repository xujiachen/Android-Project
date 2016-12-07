package com.androidServer.entity;

public class Comment {
	private String missionUsername,missionName,comment,username;
	public Comment(String missionUsername,String missionName,String comment,String username) {
		this.username = username;
		this.missionName = missionName;
		this.comment = comment;
		this.missionUsername = missionUsername;
	}
	public String getUsername() {
		return username;
	}
	public String getMissionName() {
		return missionName;
	}
	public String getComment() {
		return comment;
	}
	public String getMissionUsername() {
		return missionUsername;
	}
}
