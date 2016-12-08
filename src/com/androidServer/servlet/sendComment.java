package com.androidServer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class sendComment
 */
@WebServlet(name = "sendComment",
urlPatterns = { "/sendComment" }
)
public class sendComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private commentDatabaseImpl commentDatabase;
	private Connection connection;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendComment() {
        super();
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
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String username = request.getParameter("username");
		String mission = request.getParameter("mission");
		String commentor = request.getParameter("commentor");
		String comment = request.getParameter("comment");
		Date date = new Date();
		
		String message = "";
		
		if (!username.equals("") && !mission.equals("") && !commentor.equals("") && !comment.equals("")) {
		    Comment newComment = new Comment(commentor, mission, comment, username, date, "true");
		    message = toJsonString("Success", date);
		} else {
			message = toJsonString("Fail", date);
		}
		
		PrintWriter out = response.getWriter();
		out.print(message);
		out.flush();
		out.close();
		
	}
	
    private static String toJsonString(String status, Date date){  
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Status", status);
        jsonObject.put("Date", date);
        return jsonObject.toString();  
    }

}
