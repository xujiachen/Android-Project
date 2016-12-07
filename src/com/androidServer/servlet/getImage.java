package com.androidServer.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class getImage
 */
@WebServlet(name = "getImage",
urlPatterns = { "/getImage" })
public class getImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private userDatabaseImpl userDatabase;
    private Connection connection;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getImage() {
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
		response.setContentType("image/bmp");
		response.setCharacterEncoding("UTF-8");
		
		String username = request.getParameter("username");
		String image = "";
		String message = "";
		
		PrintWriter out = response.getWriter();
		
		try {
			FileReader reader = new FileReader(File.separator + "home" + File.separator + "xujiachen" + File.separator + "ImageServer" + File.separator + username + ".txt");
			BufferedReader bReader = new BufferedReader(reader);
			String temp = "";
			while ((temp = bReader.readLine())!=null) {
				image += temp;
			}
			
			message = toJsonString("Success", image);
			bReader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			message = toJsonString("Fail", "");
			out.println(message);
			out.flush();
			out.close();
		}
		out.println(message);
		out.flush();
		out.close();
	}
	
	private String toJsonString(String status, String image) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Status", status);
		jsonObject.put("Image", image);
		return jsonObject.toString();
	}

}
