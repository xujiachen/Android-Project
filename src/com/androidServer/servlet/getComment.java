package com.androidServer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.text.DateFormat;
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

/**
 * Servlet implementation class getComment
 */
@WebServlet(name = "getComment",
urlPatterns = { "/getComment" }
)
public class getComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private commentDatabaseImpl commentDatabase;
	private Connection connection;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getComment() {
        super();
        // TODO Auto-generated constructor stub
        GetConnection connectionClass = new GetConnection();
        connection = connectionClass.getConnection();
        commentDatabase = new commentDatabaseImpl(connection);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		
		String mission = request.getParameter("mission");
		String publisher = request.getParameter("publisher");
		
		mission = URLDecoder.decode(mission, "UTF-8");
		publisher = URLDecoder.decode(publisher, "UTF-8");
		
		
		String message = "";
		JSONArray jsonArray = new JSONArray();
		
		if (!mission.equals("") && !publisher.equals("")) {
			ArrayList<Comment> comments = commentDatabase.findAllComment(mission, publisher);
			for (int i = 0; i < comments.size(); i++) {
				Comment comment = comments.get(i);
				System.out.println(comment.getDate());
				JSONObject jsonObject = toJsonObj("Success", comment.getMissionUsername(), comment.getMissionName(), comment.getUsername(), comment.getComment(),comment.getDate(), comment.getIsAdopt());
				jsonArray.add(jsonObject);
			}
			if (comments.size() == 0) {
				JSONObject jsonObject = toJsonObj("Empty", "", "", "", "", new Date(), "");
				jsonArray.add(jsonObject);
			}
		} else {
			JSONObject jsonObject = toJsonObj("Fail", "", "", "", "", new Date(), "");
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
	
	private JSONObject toJsonObj(String status, String username, String missionName, String commentor, String comment, Date date, String isAdopt) {
		JSONObject jsonObject = new JSONObject();
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = dFormat.format(date);
		jsonObject.put("Status", status);
		jsonObject.put("Username", username);
		jsonObject.put("Missionname", missionName);
		jsonObject.put("Commentor", commentor);
		jsonObject.put("Comment", comment);
		jsonObject.put("Date", str);
		jsonObject.put("IsAdopt", isAdopt);
		return jsonObject;
	}

}
