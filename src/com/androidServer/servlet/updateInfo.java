package com.androidServer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.androidServer.Database.userDatabaseImpl;
import com.androidServer.entity.User;
import com.mysql.jdbc.Connection;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class updateInfo
 */
@WebServlet(name = "updateInfo",
urlPatterns = { "/updateInfo" }
)
public class updateInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private userDatabaseImpl userDatabase;
    private Connection connection;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateInfo() {
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
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String description = request.getParameter("description");
		
		username = URLDecoder.decode(username, "UTF-8");
		password = URLDecoder.decode(password, "UTF-8");
		description = URLDecoder.decode(description, "UTF-8");
		
		String message = "";
		
		if (!username.equals("") && !password.equals("") && !description.equals("")) {
			User old = userDatabase.findUserByName(username);
			User newUser = new User(username, password, old.getheadName(), description, old.getMoney());
			userDatabase.updateUser(newUser);
			message = toJsonString("Success");
		} else {
			message = toJsonString("Fail");
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private String toJsonString(String status) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Status", status);
		return jsonObject.toString();
	}
}
