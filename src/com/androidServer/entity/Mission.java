package com.androidServer.entity;

public class Mission {
	private String username,missionName,content,isCompleted,type,city;
	private int money;
	public Mission(String username,String missionName,String content,String isCompleted,String type,String city,int money) {
		this.username = username;
		this.missionName = missionName;
		this.content = content;
		this.isCompleted = isCompleted;
		this.type = type;
		this.city = city;
		this.money = money;
	}
	public String getUsername() {
		return username;
	}
	
	public String getMissionName() {
		return missionName;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getIsCompleted() {
		return isCompleted;
	}
	
	public String getType() {
		return type;
	}
	
	public String getCity() {
		return city;
	}
	public int getMoney() {
		return money;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
