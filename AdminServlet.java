package com.cubic.dbproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection con;
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Initialization of Servlet");
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "reddy", "reddy");
		}catch (Exception e) {
			System.out.println(e);
		}
		}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		String aname=req.getParameter("aname");
		String apass=req.getParameter("apass");
		try {
		
		PreparedStatement pst=con.prepareStatement("select * from admin");
		ResultSet rs=pst.executeQuery();
		boolean flag=false;
		HashMap<String,String> hm=new HashMap<>();
		while(rs.next()) {
			hm.put(rs.getString(1),rs.getString(2));
		}
		String pass=hm.get(aname);
		if(hm.containsKey(aname) && pass.equals(apass)) {
			flag=true;
		}
		if(flag==true) {
		out.println("<h4>Admin Record Found</h4>");
		out.println("Login Successful");
		resp.sendRedirect("StudentRegistration.html");
		}else
			out.println("<h4>Admin Details are Not Found</h4>");
	}catch (Exception e) {
		out.println("invalid Details");
	}
	}	
	@Override
	public void destroy() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
