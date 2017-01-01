package com.androidServer.Database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
		sql = "insert into missions (username,missionName,content,isCompleted,type,city,money,date) values(?,?,?,?,?,?,?,?)";
		statement = (PreparedStatement)connection.prepareStatement(sql);
		statement.setString(1, mission.getUsername());
		statement.setString(2, mission.getMissionName());
		statement.setString(3, mission.getContent());
		statement.setString(4, mission.getIsCompleted());
		statement.setString(5, mission.getType());
		statement.setString(6, mission.getCity());
		statement.setInt(7, mission.getMoney());
		statement.setTimestamp(8, new Timestamp(mission.getDate().getTime()));
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
			sql = "update missions set username=?,missionName=?, content=?,isCompleted=?,type=?,city=?,money=?,date=? where username=? and missionName=?";
			statement = (PreparedStatement)connection.prepareStatement(sql);
			statement.setString(1, mission.getUsername());
			statement.setString(2, mission.getMissionName());
			statement.setString(3, mission.getContent());
			statement.setString(4, mission.getIsCompleted());
			statement.setString(5, mission.getType());
			statement.setString(6, mission.getCity());
			statement.setInt(7, mission.getMoney());
			statement.setTimestamp(8, new Timestamp(mission.getDate().getTime()));
			statement.setString(9, mission.getUsername());
			statement.setString(10, mission.getMissionName());
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
			mission = new Mission(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),resultSet.getString(6),resultSet.getInt(7),resultSet.getTimestamp(8));
			return mission;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
	}
	public ArrayList<Mission> findMissionByUser(String username) {
		ArrayList<Mission> arrayList = new ArrayList<>();
		try {
			sql = "select * from missions where username=?";
			statement = (PreparedStatement)connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Mission mission = new Mission(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),resultSet.getString(6),resultSet.getInt(7),resultSet.getTimestamp(8));
				arrayList.add(mission);
				
			}
			return arrayList;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
	}
	public ArrayList<Map<String, String>> findAllMissionTitle(String type,String city) {
		ArrayList<Map<String, String>> arrayList = new ArrayList<>();
		try {
			sql = "select username,missionName,money,date from missions where type=? and city=?";
			statement = (PreparedStatement)connection.prepareStatement(sql);
			statement.setString(1, type);
			statement.setString(2, city);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Map<String, String> tp = new HashMap<>();
				tp.put("username", resultSet.getString(1));
				tp.put("missionName", resultSet.getString(2));
				tp.put("gold", "" + resultSet.getInt(3));
				tp.put("date", "" + resultSet.getTimestamp(4));
				arrayList.add(tp);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return arrayList;
	}
}
