package com.androidServer.entity;

public class User {
private String username,password,headName,description;
private int money;
 public User(String username,String password) {
	 this.username = username;
	 this.password = password;
	 this.headName = "headName_default";
	 this.description = "这个人很懒，什么都没写！";
	 this.money = 100;
 }
 public User(String username,String password,String headName,String description,int money) {
	 this.username = username;
	 this.password = password;
	 this.headName = headName;
	 this.description = description;
	 this.money = money;
 }
 public String getUsername() {
	 return this.username;
 }
 public String getPassword() {
	 return this.password;
 }
 
 public String getheadName() {
	 return this.headName;
 }
 
 public String getDescription() {
	 return this.description;
 }
 public int getMoney() {
	 return this.money;
 }
}