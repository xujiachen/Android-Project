package com.androidServer.Database;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
		sql = "insert into comments (missionUsername, missionName, comment,username,date,isNew,isAdopt) values(?,?,?,?,?,?,?)";
		statement = (PreparedStatement)connection.prepareStatement(sql);
		statement.setString(1, comment.getMissionUsername());
		statement.setString(2, comment.getMissionName());
		statement.setString(3, comment.getComment());
		statement.setString(4, comment.getUsername());
		statement.setTimestamp(5, new Timestamp(comment.getDate().getTime()));
		statement.setString(6, comment.getIsNew());
		statement.setString(7, comment.getIsAdopt());
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

	public void updateComment(String isNew,String isAdopt,Date date) {//此处的date指你所要更新的那条发表评论的时间
		try {
			sql = "update comments set isNew=?,isAdopt=? where date=?";
			statement = (PreparedStatement)connection.prepareStatement(sql);
			statement .setString(1, isNew );
			statement .setString(2, isAdopt );
			statement .setTimestamp(3, new Timestamp(date.getTime()));
			statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public ArrayList<Comment> findAllComment(String missionName,String missionUsername) {
		Comment comment = null;
		ArrayList<Comment> arrayList = new ArrayList<>();
		try {
			sql = "select * from comments where missionUsername=? and missionName=?";
			statement = (PreparedStatement)connection.prepareStatement(sql);
			statement.setString(1, missionUsername);
			statement.setString(2, missionName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
			comment = new Comment(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),resultSet.getString(5),resultSet.getTimestamp(6), resultSet.getString(7),resultSet.getString(8));
		//	System.out.println(comment.getMissionUsername()+comment.getMissionName()+comment.getComment()+comment.getUsername()+comment.getDate()+comment.getIsNew()+comment.getIsAdopt()+"hahah");
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