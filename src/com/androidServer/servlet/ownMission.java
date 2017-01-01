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

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.androidServer.Database.commentDatabaseImpl;
import com.androidServer.Database.missionDatabaseImpl;
import com.androidServer.Database.userDatabaseImpl;
import com.androidServer.entity.Comment;
import com.androidServer.entity.Mission;
import com.androidServer.entity.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.IO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.security.x509.PrivateKeyUsageExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ownMission
 */
@WebServlet(name = "ownMission",
urlPatterns = { "/ownMission" }
)
public class ownMission extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private missionDatabaseImpl misstionDatabase;
	private Connection connection;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ownMission() {
        super();
        GetConnection connectionClass = new GetConnection();
        connection = connectionClass.getConnection();
        misstionDatabase = new missionDatabaseImpl(connection);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		
		username = URLDecoder.decode(username, "UTF-8");
		
		String message = "";
		JSONArray jsonArray = new JSONArray();
		ArrayList<Mission> arrayList = new ArrayList<>();
		arrayList = misstionDatabase.findMissionByUser(username);
		if (!username.equals("") && arrayList.size() != 0) {	
			for (int i = 0; i < arrayList.size(); i++) {
				JSONObject jsonObject = toJsonObj("Success", arrayList.get(i).getMissionName(), arrayList.get(i).getUsername(), arrayList.get(i).getMoney(), arrayList.get(i).getDate());
				jsonArray.add(jsonObject);
			}
			//message = jsonArray.toString();
		} 
		else if (arrayList.size() == 0) {
			JSONObject jsonObject = toJsonObj("Empty", "", "", 0, new Date());
			jsonArray.add(jsonObject);
		}
		else {
			JSONObject jsonObject = toJsonObj("Fail", "", "", 0, new Date());
			jsonArray.add(jsonObject);
		}
		message = jsonArray.toString();
		
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
	private JSONObject toJsonObj(String status, String missionName, String userName, int gold, Date date) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Status", status);
		jsonObject.put("Missionname", missionName);
		jsonObject.put("Username", userName);
		jsonObject.put("Gold", gold);
		jsonObject.put("Date", date);
		return jsonObject;
	}
}
