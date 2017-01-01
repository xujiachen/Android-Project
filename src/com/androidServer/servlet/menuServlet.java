package com.androidServer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.androidServer.Database.missionDatabaseImpl;
import com.androidServer.Database.userDatabaseImpl;
import com.androidServer.entity.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Servlet implementation class menuServlet
 */
@WebServlet(name = "menuServlet",
urlPatterns = { "/menuServlet" }
)
public class menuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private userDatabaseImpl userDatebase;
	private missionDatabaseImpl misstionDatabase;
	private Connection connection;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public menuServlet() {
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
		String type = request.getParameter("type");

		String city = request.getParameter("city");
		
		type = URLDecoder.decode(type, "UTF-8");
		city = URLDecoder.decode(city, "UTF-8");

		String message = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (!type.equals("") && !city.equals("")) {
				ArrayList<Map<String, String> > missions = misstionDatabase.findAllMissionTitle(type, city);
				for (int i = 0; i < missions.size(); i++) {
					Map<String , String> mission = missions.get(i);
					Date date = strTodate(mission.get("date"));
					
					JSONObject temp = toJsonObj("Success", mission.get("missionName"), mission.get("username"), Integer.parseInt(mission.get("gold")), mission.get("date"));
					jsonArray.add(temp);
				}
				if (missions.size() == 0) {
					JSONObject temp = toJsonObj("Empty", "", "", 0, new Date().toString());
					jsonArray.add(temp);
				}
				
			} else {
				JSONObject temp = toJsonObj("Fail", "", "", 0, new Date().toString());
				jsonArray.add(temp);
			}
			message = jsonArray.toString();
			
			PrintWriter out = response.getWriter();
			out.print(message);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject temp = toJsonObj("Fail", "", "", 0, new Date().toString());
			jsonArray.add(temp);
			message = jsonArray.toString();
			
			PrintWriter out = response.getWriter();
			out.print(message);
			out.flush();
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private JSONObject toJsonObj(String status, String missionName, String userName, int gold, String date) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Status", status);
		jsonObject.put("Missionname", missionName);
		jsonObject.put("Username", userName);
		jsonObject.put("Gold", gold);
		jsonObject.put("Date", date);
		return jsonObject;
	}
	
	private Date strTodate(String strDate) throws ParseException {
	    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = format.parse(strDate);
		return date;
	}

}
