package com.androidServer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.apache.commons.logging.Log;
import net.sf.json.JSONObject;

import com.androidServer.Database.userDatabaseImpl;
import com.androidServer.entity.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class registerServlet
 */
@WebServlet(name = "registerServlet",
urlPatterns = { "/registerServlet" }
)
public class registerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private userDatabaseImpl userDatabase;
    private Connection connection;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registerServlet() {
        super();
        GetConnection connectionClass = new GetConnection();
        connection = connectionClass.getConnection();
        userDatabase = new userDatabaseImpl(connection);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String operation = request.getParameter("operation");
		
		username = URLDecoder.decode(username, "UTF-8");
		//password = URLDecoder.decode(password, "UTF-8");
		operation = URLDecoder.decode(operation, "UTF-8");
		
		User user = userDatabase.findUserByName(username);
		String message = "";
		
		if (user == null && operation.equals("register")) {
			password = URLDecoder.decode(password, "UTF-8");
		    User newUser = new User(username, password);
		    userDatabase.createUser(newUser);
		    message = toJsonString("Success");
		}
		else if (user == null && operation.equals("check")) {
			message = toJsonString("Valid username");
		}
		else {
			message = toJsonString("Username has been registered");
		}
		
		PrintWriter out = response.getWriter();
		out.println(message);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String toJsonString(String status) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Status", status);
		return jsonObject.toString();
	}
	

}
