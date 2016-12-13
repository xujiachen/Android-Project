package com.androidServer.entity;

import java.util.Date;

public class Comment {
	private String missionUsername,missionName,comment,username, isNew,isAdopt;
	private Date date;
	public Comment(String missionUsername,String missionName,String comment,String username,Date date, String isNew,String isAdopt) {
		this.username = username;
		this.missionName = missionName;
		this.comment = comment;
		this.missionUsername = missionUsername;
		this.date = date;
		this.isNew = isNew;
		this.isAdopt = isAdopt;
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
	public Date getDate() {
		return date;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String _isNew) {
		isNew = _isNew;
	}
	public String getIsAdopt() {
		return isAdopt;
	}
	public void setIsAdopt(String _isAdopt) {
		isAdopt = _isAdopt;
	}
}
