package com.androidServer.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

//import org.apache.commons.logging.Log;
import net.sf.json.JSONObject;

import com.androidServer.Database.userDatabaseImpl;
import com.androidServer.entity.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MultipartDataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class sendImage
 */
@WebServlet(name = "sendImage",
urlPatterns = { "/sendImage" })
public class sendImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private userDatabaseImpl userDatabase;
    private Connection connection;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendImage() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/html");
//		response.setCharacterEncoding("UTF-8");
//		request.setCharacterEncoding("UTF-8");
//		
//		String strImg = request.getParameter("image");
//		String username = request.getParameter("username");
//		
//		//String name = request.getParameter("name");
//		//byte[] bytes = strImg.getBytes();
//		String message = "";
//		String route = File.separator + "home" + File.separator + "xujiachen" + File.separator + "ImageServer" + File.separator + username + ".txt";
//	    File dir = new File(route);
//	    
//	    BufferedOutputStream bos = null;  
//        FileOutputStream fos = null; 
//	    if (dir.exists()) {
//	    	dir.delete();
//	    }
//	    try {
//	    	dir.createNewFile();
//	    	
//	    	System.out.println(strImg);
//	    	
//	    	byte[] bytes = strImg.getBytes("UTF-8");
//	    	fos = new FileOutputStream(dir);
//	    	bos = new BufferedOutputStream(fos);
//	    	bos.write(bytes);
//	    	
//	    	User user = userDatabase.findUserByName(username);
//	    	User newUser = new User(user.getUsername(), user.getPassword(), route, user.getDescription(), user.getMoney());
//	    	userDatabase.updateUser(newUser);
//	    	message = toJsonString("Success");
//	    	
//	    	PrintWriter out = response.getWriter();
//	    	out.println(message);
//	    	out.flush();
//	    	out.close();
//	    } catch (Exception e) {
//			e.printStackTrace();
//			message = toJsonString("Fail");
//			PrintWriter out = response.getWriter();
//	    	out.println(message);
//	    	out.flush();
//	    	out.close();
//		} finally {  
//            if (bos != null) {  
//                try {  
//                    bos.close();  
//                } catch (IOException e1) {  
//                    e1.printStackTrace();  
//                }  
//            }  
//            if (fos != null) {  
//                try {  
//                    fos.close();  
//                } catch (IOException e1) {  
//                    e1.printStackTrace();  
//                }  
//            }  
//        }
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("Text/html");
		request.setCharacterEncoding("UTF-8");
		
		InputStream is = null;     
		
//	    String contentStr = "";     
//	    try {         
//		    is = request.getInputStream();
//		    byte[] temp = new byte[1024];
//		    System.out.println(is.read(temp));
//		    
//			contentStr =  new String(temp);
//			System.out.println(contentStr);
//	    } catch (IOException e) { 
//			e.printStackTrace();     
//	    }     
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		String message = "";
		String path = File.separator + "home" + File.separator + "xujiachen" + File.separator + "ImageServer";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		factory.setRepository(file);
		factory.setSizeThreshold(1024 * 1024);
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		
	    String value = "";
		try {		
//			ServletRequestContext sContext = new ServletRequestContext(request);
//
//			List<FileItem> list = (List<FileItem>)upload.parseRequest(new ServletRequestContext(request));
//			for (FileItem item : list) {
//				//String name = item.getFieldName();
//				System.out.println("hahaahahahah" + item.toString());
//				if (item.isFormField()) {
//					value = item.getString();
//					System.out.println(value);
//				} else {
//					String fieldName = item.getFieldName();
//					String fileName = item.getName();
//					File image = new File(File.separator + "home" + File.separator + "xujiachen" + File.separator + "ImageServer" + File.separator + "test");
//					if (image.exists()) {
//						image.delete();		
//					}
//					image.createNewFile();
//					item.write(image);
//					
//				}
//			}
//			message = toJsonString("Success");
//			PrintWriter out = response.getWriter();
//			out.print(message);
//			out.flush();
//			out.close();
			
			if (ServletFileUpload.isMultipartContent(request)) {
			    ServletFileUpload fileUpload = new ServletFileUpload();
			    FileItemIterator items = fileUpload.getItemIterator(request);
			    // iterate items
			    while (items.hasNext()) {
			        FileItemStream item = items.next();
			        is = item.openStream();
				    byte[] temp = new byte[1024];
				    System.out.println(is.read(temp));
				    
					String contentStr =  new String(temp);
					System.out.println(contentStr);
//			        if (!item.isFormField()) {
//			            is = item.openStream();
//			        }
			    }
				message = toJsonString("Success");
				PrintWriter out = response.getWriter();
				out.print(message);
				out.flush();
				out.close();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			message = toJsonString("Fail");
			PrintWriter out = response.getWriter();
			out.print(message);
			out.flush();
			out.close();
		}
		
	}

	private String toJsonString(String status) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Status", status);
		return jsonObject.toString();
	}
}
