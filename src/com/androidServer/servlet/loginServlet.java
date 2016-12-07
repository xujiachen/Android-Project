package com.androidServer.servlet;

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
/**
 * Servlet implementation class helloworld
 */
@WebServlet(name = "loginServlet",
urlPatterns = { "/loginServlet" }
)
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private userDatabaseImpl userDatabase;
    private Connection connection;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
        GetConnection connectionClass = new GetConnection();
        connection = connectionClass.getConnection();
        userDatabase = new userDatabaseImpl(connection);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userDatabase.findUserByName(username);
		/*Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);*/
		String message = "";
		if (user != null && user.getPassword().equals(password)) {
		    message = toJsonString("Success", user.getUsername(), user.getDescription(), user.getMoney(), user.getPassword());
		} 
		else if (user != null && !user.getPassword().equals(password)) {
			message = toJsonString("Wrong password", "", "", -1, "");
		} else {
			message = toJsonString("Please register your account first", "", "", -1, "");
		}
	
		PrintWriter out = response.getWriter();
		out.println(message);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		//String username = 
	}
	//转换成json
    private static String toJsonString(String status, String userName, String description, int money, String password){  
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Status", status);
        if (status.equals("Success")) {
        	jsonObject.put("UserName", userName);
        	jsonObject.put("Description", description);
        	jsonObject.put("Money", money);
        	jsonObject.put("Password", password);
        }
        return jsonObject.toString();  
    }
	
    /*private static Map<String, Object> toKeyVal(String status, String userName, String description, String money, String password){  
       
        Map<String,Object> map=new HashMap<String, Object>();  
        map.put("code", code);  
        map.put("msg", msg);  
        map.put("data", obj);  
          
        return map;  
     }*/
    
  
}
