package com.androidServer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.androidServer.Database.missionDatabaseImpl;
import com.androidServer.entity.Mission;
import com.mysql.jdbc.Connection;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class sendMission
 */
@WebServlet(name = "sendMission",
urlPatterns = { "/sendMission" })
public class sendMission extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private missionDatabaseImpl missionDatabase;
    private Connection connection;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendMission() {
        super();
        GetConnection connectionClass = new GetConnection();
        connection = connectionClass.getConnection();
        missionDatabase = new missionDatabaseImpl(connection);
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
		//doGet(request, response);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String missionname = request.getParameter("missionname");
		String content = request.getParameter("content");
		String type = request.getParameter("type");
		String city = request.getParameter("city");
		String money = request.getParameter("gold");
		
		username = URLDecoder.decode(username, "UTF-8");
		missionname = URLDecoder.decode(missionname, "UTF-8");
		content = URLDecoder.decode(content, "UTF-8");
		type = URLDecoder.decode(type, "UTF-8");
		city = URLDecoder.decode(city, "UTF-8");
	    int gold = Integer.parseInt(money);
		
	    String message = "";
	    
		if (!username.equals("") && !missionname.equals("") && !content.equals("") && !type.equals("") && city.equals("")) {
			Mission newMission = new Mission(username, missionname, content, "false", type, city, gold, new Date());
			missionDatabase.createMission(newMission);
			
			message = toJsonString("Success");
		} else {
			message = toJsonString("Fail");
		}
		
		PrintWriter out = response.getWriter();
		out.print(message);
		out.flush();
		out.close();
	}
	
	private String toJsonString(String status) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Status", status);
		return jsonObject.toString();
	}

}
