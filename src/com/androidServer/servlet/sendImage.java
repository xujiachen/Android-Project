package com.androidServer.servlet;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

//import org.apache.commons.logging.Log;
import net.sf.json.JSONObject;

import com.androidServer.Database.userDatabaseImpl;
import com.androidServer.entity.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class sendImage
 */
@WebServlet(name = "sendImage",
urlPatterns = { "/sendImage" })
public class sendImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private userDatabaseImpl userDatabase;
    private Connection connection;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendImage() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String strImg = request.getParameter("image");
		String username = request.getParameter("username");
		//String name = request.getParameter("name");
		//byte[] bytes = strImg.getBytes();
		String message = "";
		String route = File.separator + "home" + File.separator + "xujiachen" + File.separator + "ImageServer" + File.separator + username + ".txt";
	    File dir = new File(route);
	    if (dir.exists()) {
	    	dir.delete();
	    }
	    try {
	    	dir.createNewFile();
	    	
	    	PrintStream ps = new PrintStream(new FileOutputStream(dir));
	    	ps.print(strImg);
	    	ps.close();
	    	
	    	User user = userDatabase.findUserByName(username);
	    	User newUser = new User(user.getUsername(), user.getPassword(), route, user.getDescription(), user.getMoney());
	    	userDatabase.updateUser(newUser);
	    	message = toJsonString("Success");
	    	
	    	PrintWriter out = response.getWriter();
	    	out.println(message);
	    	out.flush();
	    	out.close();
	    } catch (Exception e) {
			e.printStackTrace();
			message = toJsonString("Fail");
			PrintWriter out = response.getWriter();
	    	out.println(message);
	    	out.flush();
	    	out.close();
		}
	    
	    
	}

	private String toJsonString(String status) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Status", status);
		return jsonObject.toString();
	}
}
