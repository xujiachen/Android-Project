package com.androidServer.Database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;

import com.androidServer.entity.User;

public class userDatabaseImpl {
private Connection connection;
private String sql;
private PreparedStatement statement;
public userDatabaseImpl(Connection connection) {
	this.connection = connection;
	sql = null;
}

public void createUser(User user) {
	try {
	sql = "insert into users (username, password, headName,description,money) values(?,?,?,?,?)";
	statement = (PreparedStatement)connection.prepareStatement(sql);
	statement.setString(1, user.getUsername());
	statement.setString(2, user.getPassword());
	statement.setString(3, user.getheadName());
	statement.setString(4, user.getDescription());
	statement.setInt(5, user.getMoney());
	statement.executeUpdate();
	statement.close();
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}
public void deleteUser(String username) {
	try {
		sql = "delete from users where username=?";
		statement = (PreparedStatement)connection.prepareStatement(sql);
		statement.setString(1, username);
		statement.executeUpdate();
		statement.close();
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}

public void updateUser(User user) {
	try {
		sql = "update users set username=?,password=?, headName=?,description=?,money=? where username=?";
		statement = (PreparedStatement)connection.prepareStatement(sql);
		statement.setString(1, user.getUsername());
		statement.setString(2, user.getPassword());
		statement.setString(3, user.getheadName());
		statement.setString(4, user.getDescription());
		statement.setInt(5, user.getMoney());
		statement.setString(6, user.getUsername());
		statement.executeUpdate();
		statement.close();
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}
public User findUserByName(String username) {
	User user = null;
	try {
		sql = "select * from users where username=?";
		statement = (PreparedStatement)connection.prepareStatement(sql);
		statement.setString(1, username);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next())
		user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
		return user;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
}
}