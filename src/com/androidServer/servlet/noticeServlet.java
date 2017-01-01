package com.androidServer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.text.Format;
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

import com.androidServer.Database.commentDatabaseImpl;
import com.androidServer.Database.missionDatabaseImpl;
import com.androidServer.Database.userDatabaseImpl;
import com.androidServer.entity.Comment;
import com.androidServer.entity.Mission;
import com.androidServer.entity.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sun.prism.shader.FillRoundRect_Color_AlphaTest_Loader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class noticeServlet
 */
@WebServlet(name = "noticeServlet",
urlPatterns = { "/noticeServlet" }
)
public class noticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private missionDatabaseImpl missionDatabase;
	private commentDatabaseImpl commentDatabase;
	private Connection connection;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public noticeServlet() {
        super();
        GetConnection connectionClass = new GetConnection();
        connection = connectionClass.getConnection();
        missionDatabase = new missionDatabaseImpl(connection);
        commentDatabase = new commentDatabaseImpl(connection);
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
		
		String message = toJsonString("No new message", username, "", "", "", new Date());
		
		ArrayList<Mission> missions = missionDatabase.findMissionByUser(username);
		for (int i = 0; i < missions.size(); i++) {
			ArrayList<Comment> comments = commentDatabase.findAllComment(missions.get(i).getMissionName(), username);
			for (int j = 0; j < comments.size(); j++) {
				Comment comment = comments.get(j);
				//toJsonString("No new message", username, "", "", "", new Date(""));
				if (comment.getIsNew().equals("true")) {
					message = toJsonString("Success", username, comment.getMissionName(), comment.getMissionUsername(), comment.getComment(), comment.getDate());
					//comment.setIsNew("false");
				    commentDatabase.updateComment("false", comment.getIsAdopt(), comment.getDate());
					i = missions.size() + 1;
					break;
				}
			}
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
	
	private String toJsonString(String status, String username, String missionName, String commentor, String comment,Date date) {
		Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Status", status);
		jsonObject.put("Username", username);
		jsonObject.put("Missionname", missionName);
		jsonObject.put("Commentor", commentor);
		jsonObject.put("Comment", comment);
		jsonObject.put("Date", time);
		return jsonObject.toString();
	}
	
	

}
