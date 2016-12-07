package com.androidServer.Database;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.androidServer.entity.Comment;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class commentDatabaseImpl {
	private Connection connection;
	private String sql;
	private PreparedStatement statement;
	public commentDatabaseImpl(Connection connection) {
		this.connection = connection;
		sql = null;
	}

	public void createComment(Comment comment) {
		try {
		sql = "insert into comments (missionUsername, missionName, comment,username) values(?,?,?,?)";
		statement = (PreparedStatement)connection.prepareStatement(sql);
		statement.setString(1, comment.getMissionUsername());
		statement.setString(2, comment.getMissionName());
		statement.setString(3, comment.getComment());
		statement.setString(4, comment.getUsername());
		statement.executeUpdate();
		statement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void deleteComment(String username,String missionName,String missionUsername) {
		try {
			sql = "delete from comments where username=? and missionName=? and missionUsername=?";
			statement = (PreparedStatement)connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, missionName);
			statement.setString(3, missionUsername);
			statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/*public void updateComment(Comment comment) {
		try {
			sql = "update comments set username=?,missionName=?, comment=? where username=? and missionName=?";
			statement = (PreparedStatement)connection.prepareStatement(sql);
			statement.setString(1, comment.getUsername());
			statement.setString(2, comment.getMissionName());
			statement.setString(3, comment.getComment());
			statement.setString(4, comment.getUsername());
			statement.setString(5, comment.getMissionName());
			statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/
	public ArrayList<Comment> findAllComment(String username,String missionName,String missionUsername) {
		Comment comment = null;
		ArrayList<Comment> arrayList = new ArrayList<>();
		try {
			sql = "select * from comments where missionUsername=? and missionName=? and username=?";
			statement = (PreparedStatement)connection.prepareStatement(sql);
			statement.setString(1, missionUsername);
			statement.setString(2, missionName);
			statement.setString(3, username);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
			comment = new Comment(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),resultSet.getString(4));
			arrayList.add(comment);
			}
			return arrayList;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
		//return null;
	}
}
