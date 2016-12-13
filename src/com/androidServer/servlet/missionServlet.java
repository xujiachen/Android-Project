package com.androidServer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.androidServer.Database.commentDatabaseImpl;
import com.androidServer.Database.missionDatabaseImpl;
import com.androidServer.Database.userDatabaseImpl;
import com.androidServer.entity.Mission;
import com.androidServer.entity.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.security.x509.PrivateKeyUsageExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class missionServlet
 */
@WebServlet(name = "missionServlet",
urlPatterns = { "/missionServlet" }
)
public class missionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private missionDatabaseImpl misstionDatabase;
	//private commentDatabaseImpl commentDatabase;
	private Connection connection;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public missionServlet() {
        super();
        // TODO Auto-generated constructor stub
        GetConnection connectionClass = new GetConnection();
        connection = connectionClass.getConnection();
        misstionDatabase = new missionDatabaseImpl(connection);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		//String username = request.getParameter("username");
		String missionName = request.getParameter("mission");
		String publisher = request.getParameter("publisher");
		
		missionName = URLDecoder.decode(missionName, "UTF-8");
		publisher = URLDecoder.decode(publisher, "UTF-8");
		
		String message = "";
		
		if (!missionName.equals("") && !publisher.equals("")) {
			Mission mission = misstionDatabase.findMission(publisher, missionName);
			message = toJsonString("Success", mission.getContent(), mission.getIsCompleted(), mission.getType(), mission.getCity(), mission.getMoney(), mission.getDate().toString());
		} else {
			message = toJsonString("Fail", "", "", "", "", 0, new Date().toString());
		}
		
		PrintWriter out = response.getWriter();
		out.print(message);
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
	
	private String toJsonString(String status, String content, String isComplete, String type, String city, int gold, String date) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Status", status);
		jsonObject.put("Content", content);
		jsonObject.put("IsComplete", isComplete);
		jsonObject.put("Type", type);
		jsonObject.put("City", city);
		jsonObject.put("Gold", gold);
		jsonObject.put("Date", date);
		return jsonObject.toString();
	}

}
