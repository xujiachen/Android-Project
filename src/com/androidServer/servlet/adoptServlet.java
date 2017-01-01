package com.androidServer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
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


/**
 * Servlet implementation class adoptServlet
 */
@WebServlet(name = "adoptServlet",
urlPatterns = { "/adoptServlet" }
)
public class adoptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private commentDatabaseImpl commentDatabase;
	private userDatabaseImpl userDatebase;
	private missionDatabaseImpl missionDatabase;
	private Connection connection;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adoptServlet() {
        super();
        GetConnection connectionClass = new GetConnection();
        connection = connectionClass.getConnection();
        commentDatabase = new commentDatabaseImpl(connection);
        userDatebase = new userDatabaseImpl(connection);
        missionDatabase = new missionDatabaseImpl(connection);
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
		String username = request.getParameter("username");
		String strdate = request.getParameter("date");
	    String gold = request.getParameter("gold");
	    
	    mission = URLDecoder.decode(mission, "UTF-8");
	    publisher = URLDecoder.decode(publisher, "UTF-8");
	    username = URLDecoder.decode(username, "UTF-8");
	    gold = URLDecoder.decode(gold, "UTF-8");
	    
	    DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String tempDate;
		try {
			
			String message = "";
			
			ArrayList<Comment> arrayList = new ArrayList<>();
			
			if (!mission.equals("") && !publisher.equals("") && !username.equals("") && !request.getParameter("date").equals("") && !gold.equals("")) {
				arrayList = commentDatabase.findAllComment(mission, publisher);
				//System.out.println(arrayList.size());
				for (int i = 0; i < arrayList.size(); i++) {
				    Comment temp = arrayList.get(i);
				    //date = dFormat.parse(strdate);
				    tempDate = dFormat.format(temp.getDate());
				    
				    if (temp.getUsername().equals(username) && tempDate.equals(strdate)) {
				    	User askerUser = userDatebase.findUserByName(publisher);
				    	User askerStatus = new User(askerUser.getUsername(), askerUser.getPassword(), askerUser.getheadName(), askerUser.getDescription(), askerUser.getMoney() - Integer.parseInt(gold));
				    	userDatebase.updateUser(askerStatus);
				    	
				    	User adoptedUser = userDatebase.findUserByName(username);
				    	User newStatus = new User(adoptedUser.getUsername(), adoptedUser.getPassword(), adoptedUser.getheadName(), adoptedUser.getDescription(), adoptedUser.getMoney() + Integer.parseInt(gold));
				    	userDatebase.updateUser(newStatus);
//				    	temp.setIsAdopt("true_new");
				    	commentDatabase.updateComment(temp.getIsNew(), "new_true", temp.getDate());
				    	
				    	Mission newMission = missionDatabase.findMission(publisher, mission);
				    	missionDatabase.updateMission(new Mission(newMission.getUsername(), newMission.getMissionName(), newMission.getContent(), "true", newMission.getType(), newMission.getCity(), newMission.getMoney(), newMission.getDate()));
				    	
				    	message = toJsonString("Success");
				    	break;
				    }
				    
				}
				if (message.equals("")) {
			    	message = toJsonString("Empty");
			    }
			} else {
				message = toJsonString("Fail");
			}
			
			PrintWriter out = response.getWriter();
			out.print(message);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private static String toJsonString(String status){  
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Status", status);
        return jsonObject.toString();  
    }

}
