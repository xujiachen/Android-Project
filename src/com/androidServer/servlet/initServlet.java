package com.androidServer.servlet;

import java.io.IOException;
import java.sql.ResultSet;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.androidServer.Database.userDatabaseImpl;
import com.androidServer.entity.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class initServlet
 */
@WebServlet(name = "initServlet",
urlPatterns = { "/initServlet" }
)
public class initServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private userDatabaseImpl userDatabase;
    private Connection connection;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public initServlet() {
        super();
        // TODO Auto-generated constructor stub
        GetConnection connectionClass = new GetConnection();
        connection = connectionClass.getConnection();
        userDatabase = new userDatabaseImpl(connection);
        try {
        	ResultSet rs = connection.getMetaData().getTables(null, null, "users", null);
        	if (!rs.next()) {
        	 createTables(connection);
             User user = new User("wujy", "122521", "no pic", "this is a test data", 10);
             userDatabase.createUser(user);
        }
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	  public void createTables(Connection con) {
	    	PreparedStatement statement = null;
	    	try {
	    	//建立用户表
	    	String sql = "CREATE TABLE if not exists users(username varchar(100) not null,password varchar(100) not null,headName varchar(100) not null,description TEXT,money int not null,"
	    			+ "PRIMARY KEY(username))";
	    	statement = (PreparedStatement)con.prepareStatement(sql);
	    	statement.execute();
	    	
	    	sql = "CREATE TABLE if not exists missions(username varchar(100) not null,missionName TEXT not null,content TEXT,isCompleted varchar(100) not null,type varchar(100) not null,city varchar(100) not null,money int not null,PRIMARY KEY(username,missionName(500)))";
	    	statement = (PreparedStatement)con.prepareStatement(sql);
	    	statement.execute();
	    	
	    	sql = "CREATE TABLE if not exists comments(id int(20) not null AUTO_INCREMENT,missionUsername varchar(100) not null,missionName TEXT not null,comment TEXT not null,username varchar(100) not null,PRIMARY KEY(id))";
	    	statement = (PreparedStatement)con.prepareStatement(sql);
	    	statement.execute();
	    	statement.close();
	    	}catch (Exception e) {
				// TODO: handle exception
	    		e.printStackTrace();
			}

	    }

}
