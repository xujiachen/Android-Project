package com.androidServer.Database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.androidServer.entity.Mission;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sun.org.apache.regexp.internal.recompile;

public class missionDatabaseImpl {
	private Connection connection;
	private String sql;
	private PreparedStatement statement;
	public missionDatabaseImpl(Connection connection) {
		this.connection = connection;
		sql = null;
	}

	public void createMission(Mission mission) {
		try {
		sql = "insert into missions (username,missionName,content,isCompleted,type,city,money) values(?,?,?,?,?,?,?)";
		statement = (PreparedStatement)connection.prepareStatement(sql);
		statement.setString(1, mission.getUsername());
		statement.setString(2, mission.getMissionName());
		statement.setString(3, mission.getContent());
		statement.setString(4, mission.getIsCompleted());
		statement.setString(5, mission.getType());
		statement.setString(6, mission.getCity());
		statement.setInt(7, mission.getMoney());
		statement.executeUpdate();
		statement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void deleteMission(String username,String missionName) {
		try {
			sql = "delete from missions where username=? and missionName=?";
			statement = (PreparedStatement)connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, missionName);
			statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void updateMission(Mission mission) {
		try {
			sql = "update missions set username=?,missionName=?, content=?,isCompleted=?,type=?,city=?,money=? where username=? and missionName=?";
			statement = (PreparedStatement)connection.prepareStatement(sql);
			statement.setString(1, mission.getUsername());
			statement.setString(2, mission.getMissionName());
			statement.setString(3, mission.getContent());
			statement.setString(4, mission.getIsCompleted());
			statement.setString(5, mission.getType());
			statement.setString(6, mission.getCity());
			statement.setInt(7, mission.getMoney());
			statement.setString(8, mission.getUsername());
			statement.setString(9, mission.getMissionName());
			statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public Mission findMission(String username,String missionName) {
		Mission mission = null;
		try {
			sql = "select * from missions where username=? and missionName=?";
			statement = (PreparedStatement)connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, missionName);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next())
			mission = new Mission(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),resultSet.getString(6),resultSet.getInt(7));
			return mission;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
	}
	public ArrayList<Map<String, String>> findAllMissionTitle(String type,String city) {
		ArrayList<Map<String, String>> arrayList = new ArrayList<>();
		try {
			sql = "select username,missionName from missions where type=? and city=?";
			statement = (PreparedStatement)connection.prepareStatement(sql);
			statement.setString(1, type);
			statement.setString(2, city);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Map<String, String> tp = new HashMap<>();
				tp.put("username", resultSet.getString(1));
				tp.put("missionName", resultSet.getString(2));
				arrayList.add(tp);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return arrayList;
	}
}
